package com.ra.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserPasswordRequestDTO {
    private String oldPass;
//    @Min(value = 4, message = "Mật khẩu không được ít hơn 4 ký tự!!!")
    private String newPass;
    private String confirmPass;
}
