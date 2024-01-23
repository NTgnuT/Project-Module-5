
package com.ra.service.user;


import com.ra.exception.CustomException;
import com.ra.model.dto.request.UserInfoRequestDTO;
import com.ra.model.dto.request.UserRegisterDTO;
import com.ra.model.dto.request.UserRequestDTO;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.model.dto.response.UserInfoResponseDTO;
import com.ra.model.dto.response.UserResponseAllDTO;
import com.ra.model.dto.response.UserResponseDTO;
import com.ra.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User register (UserRegisterDTO userRegisterDTO) throws CustomException;
    UserResponseDTO login (UserRequestDTO userRequest) throws CustomException;
    Boolean changeStatus (Long id) throws CustomException;
    Page<UserResponseAllDTO> findAll (Pageable pageable);
    Page<UserResponseAllDTO> searchByName (Pageable pageable, String name);
    User findById (Long id) throws CustomException;
    UserInfoResponseDTO changeInfoUser (UserInfoRequestDTO userInfoRequestDTO, User userLogin) throws CustomException;
    void changePassword (User user, String pass);
}

