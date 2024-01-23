
package com.ra.controller;


import com.ra.exception.CustomException;
import com.ra.model.dto.request.UserRegisterDTO;
import com.ra.model.dto.request.UserRequestDTO;
import com.ra.model.dto.response.UserResponseDTO;
import com.ra.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody UserRequestDTO userRequest) throws CustomException {
        UserResponseDTO userResponse = userService.login(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) throws CustomException {
        return new ResponseEntity<>(userService.register(userRegisterDTO), HttpStatus.OK);
    }
}

