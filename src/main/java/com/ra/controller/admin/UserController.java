package com.ra.controller.admin;

import com.ra.exception.CustomException;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.model.dto.response.UserResponseAllDTO;
import com.ra.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseAllDTO>> getAll (@RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "5") int size,
                                                            @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                            @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<UserResponseAllDTO> userResponseAllDTOS = userService.findAll(pageable);
        return new ResponseEntity<>(userResponseAllDTOS, HttpStatus.OK);
    }
    @GetMapping("/users/search")
    public ResponseEntity<Page<UserResponseAllDTO>> search (@RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "5") int size,
                                                            @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                            @RequestParam(name = "order", defaultValue = "desc") String order,
                                                            @RequestParam(name = "search") String search) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<UserResponseAllDTO> userResponseAllDTOS = userService.searchByName(pageable, search);
        return new ResponseEntity<>(userResponseAllDTOS, HttpStatus.OK);
    }


    @PatchMapping("/users/{id}")
    public ResponseEntity<?> changeStatus (@PathVariable("id") Long id) throws CustomException {
        userService.changeStatus(id);
        String mess = "Thay đổi trạng thái thành công.";
        return new ResponseEntity<>(mess, HttpStatus.OK);
    }
}
