package com.ra.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HotelRequestDTO {
    private Long id;
    @Column (unique = true)
    @NotBlank(message = "Tên khách sạn không được để trống!!!")
    private String hotelName;
    private MultipartFile image;
    @NotBlank(message = "Thêm cái địa chỉ khách sạn vào!!!")
    private String address;
    private String description;
    @Column (columnDefinition = "boolean default true")
    private Boolean status = true;
}
