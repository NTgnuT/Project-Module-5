
package com.ra.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRequestDTO {
    @NotNull(message = "Tên đăng nhập không được để trống!!!")
    private String username;
    @NotNull(message = "Mật khẩu không được để trống!!!")
    private String password;
}

