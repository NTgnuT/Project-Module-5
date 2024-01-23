
package com.ra.service.user;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.UserInfoRequestDTO;
import com.ra.model.dto.request.UserRegisterDTO;
import com.ra.model.dto.request.UserRequestDTO;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.model.dto.response.UserInfoResponseDTO;
import com.ra.model.dto.response.UserResponseAllDTO;
import com.ra.model.dto.response.UserResponseDTO;
import com.ra.model.entity.Product;
import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import com.ra.repository.UserRepository;
import com.ra.sercurity.jwt.JWTProvider;
import com.ra.sercurity.user_principle.UserPrinciple;
import com.ra.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private RoleService roleService;
    @Override
    public User register(UserRegisterDTO userRegisterDTO) throws CustomException {
        // Mã hóa mật khẩu
        userRegisterDTO.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        // register của user thì coi là USER
        Set<Role> roles = new HashSet<>();

        roles.add(roleService.findByRoleName("USER"));

        // Kiểm tra tên đăng nhập
        if (userRepository.existsByUserName(userRegisterDTO.getUserName())) {
            throw new CustomException("Tên đăng nhập đã tồn tại!!!");
        }

        // Kiểm tra email
        if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
            throw new CustomException("Email đã tồn tại !!!");
        }

        User newUser = new User();
        newUser.setUserName(userRegisterDTO.getUserName());
        newUser.setFullName(userRegisterDTO.getFullName());
        newUser.setPassword(userRegisterDTO.getPassword());
        newUser.setEmail(userRegisterDTO.getEmail());
        newUser.setAge(userRegisterDTO.getAge());
        newUser.setPhoneNumber(userRegisterDTO.getPhoneNumber());
        newUser.setStatus(userRegisterDTO.getStatus());
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }

    @Override
    public UserResponseDTO login(UserRequestDTO userRequest) throws CustomException {

        User user = userRepository.findByUserName(userRequest.getUsername());

        if (user == null || !user.getStatus()) {
            throw new CustomException("Tài khoản không hợp lệ hoặc đã bị khóa, vui lòng kiểm tra lại!!!");
        }

//        String encodingPass = passwordEncoder.encode(user.getPassword());
//        if (encodingPass.equals(userRequest.getPassword())) {
//            throw new CustomException("Sai mật khẩu!!!");
//        }

        Authentication authentication;

        authentication = authenticationProvider
                .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

    return UserResponseDTO.builder().token(jwtProvider.generateToken(userPrinciple))
            .userName(userPrinciple.getUsername())
            .email(userPrinciple.getEmail())
            .age(userPrinciple.getAge())
            .phoneNumber(userPrinciple.getPhoneNumber())
            .bookings(userPrinciple.getBookings())
            .roles(userPrinciple.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
            .build();
    }

    @Override
    public Boolean changeStatus(Long id) throws CustomException {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("Không tìm thấy tài khoản !!!"));
        if (user.getId() == 1) {
            throw new CustomException("Không thể khóa tài khoản này!!!");
        }
        user.setStatus(!user.getStatus());
        userRepository.save(user);
        return true;
    }

    @Override
    public Page<UserResponseAllDTO> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(UserResponseAllDTO::new);
    }

    @Override
    public Page<UserResponseAllDTO> searchByName(Pageable pageable, String name) {
        Page<User> userPage = userRepository.findAllByUserNameContainingIgnoreCase(pageable, name);
        return userPage.map(user -> new UserResponseAllDTO(user));
    }

    @Override
    public User findById(Long id) throws CustomException {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new CustomException("Không tìm thấy người dùng!!!"));
    }

    @Override
    public UserInfoResponseDTO changeInfoUser(UserInfoRequestDTO userInfoRequestDTO, User userLogin) throws CustomException {
        if (!userLogin.getUserName().equals(userInfoRequestDTO.getUserName())){
            if (userRepository.existsByUserName(userInfoRequestDTO.getUserName())) {
                throw new CustomException("Tên đăng nhập đã tồn tại!!!");
            }
            userLogin.setUserName(userInfoRequestDTO.getUserName());
        }
        userLogin.setFullName(userInfoRequestDTO.getFullName());
        userLogin.setAge(userInfoRequestDTO.getAge());
        userLogin.setEmail(userInfoRequestDTO.getEmail());
        userLogin.setPhoneNumber(userInfoRequestDTO.getPhoneNumber());
        userRepository.save(userLogin);
        return new UserInfoResponseDTO(userLogin);
    }

    @Override
    public void changePassword(User user, String pass) {
        String encodingPass = passwordEncoder.encode(pass);
        user.setPassword(encodingPass);
        userRepository.save(user);
    }
}

