package com.ra.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomRequestDTO {
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "Số phòng không được để trống")
    private String roomNo;
    private MultipartFile file;
    @Min(value = 1, message = "Giá phòng phải lớn hơn 0")
    private Float price;
    private String description;
    @Column(columnDefinition = "boolean default true")
    private Boolean status = true;
    private Long hotelId;
    private Long typeRoomId;
}
