package com.ra.controller.client;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.UserInfoRequestDTO;
import com.ra.model.dto.request.UserPasswordRequestDTO;
import com.ra.model.dto.response.UserInfoResponseDTO;
import com.ra.model.entity.User;
import com.ra.sercurity.user_principle.UserPrinciple;
import com.ra.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class InfoUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public ResponseEntity<UserInfoResponseDTO> showInfoUser (Authentication authentication) throws CustomException {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userService.findById(userPrinciple.getId());

        UserInfoResponseDTO infoResponseDTO = new UserInfoResponseDTO(user);
        return new ResponseEntity<>(infoResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/account")
    public ResponseEntity<UserInfoResponseDTO> changeInfo (@RequestBody UserInfoRequestDTO userInfoRequestDTO, Authentication authentication) throws CustomException {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userService.findById(userPrinciple.getId());

        UserInfoResponseDTO infoResponseDTO = userService.changeInfoUser(userInfoRequestDTO, user);
        return new ResponseEntity<>(infoResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/account/change-password")
    public ResponseEntity<?> changePassword (@Valid Authentication authentication, @RequestBody UserPasswordRequestDTO passwordRequestDTO) throws CustomException {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userService.findById(userPrinciple.getId());

        // Kiểm tra mật khẩu hiện tại
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(passwordRequestDTO.getOldPass(), user.getPassword())) {
            throw new CustomException("Mật khẩu cũ không trùng khớp !!!");
        }

        if (passwordRequestDTO.getNewPass().length() < 4) {
            throw new CustomException("Mật khẩu không được ít hơn 4 ký tự!!!");
        }

        // Kiểm tra confirm password
        if (!passwordRequestDTO.getNewPass().equals(passwordRequestDTO.getConfirmPass())) {
            throw new CustomException("Mật khẩu không trùng khớp");
        }

        userService.changePassword(user, passwordRequestDTO.getNewPass());
        return new ResponseEntity<>("Thay đổi mật khẩu thành công", HttpStatus.OK);
    }
}
