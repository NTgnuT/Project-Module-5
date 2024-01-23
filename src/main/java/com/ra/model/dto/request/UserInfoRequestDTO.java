package com.ra.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserInfoRequestDTO {
    @NotBlank(message = "Không được để trống")
    private String userName;
    private String fullName;
    @Email(message = "Email không hợp lệ")
    private String email;
    @Min(value = 18, message = "Phải trên 18 tuổi")
    private Integer age;
    @Pattern(regexp = "(((\\+|)84)|0)(3|5|7|8|9)+([0-9]{8})\\b", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;
}
