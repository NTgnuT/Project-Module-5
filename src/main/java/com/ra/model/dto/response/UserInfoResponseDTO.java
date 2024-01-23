package com.ra.model.dto.response;

import com.ra.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserInfoResponseDTO {
    private String userName;
    private String fullName;
    private String email;
    private Integer age;
    private String phoneNumber;

    public UserInfoResponseDTO(User user) {
        this.userName = user.getUserName();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.phoneNumber = user.getPhoneNumber();
    }
}
