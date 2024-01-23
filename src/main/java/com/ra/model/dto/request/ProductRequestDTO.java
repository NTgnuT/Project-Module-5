package com.ra.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductRequestDTO {
    private Long id;
    @Column (unique = true)
    @NotBlank (message = "Tên dịch vụ không được để trống!!!")
    private String productName;
    @Min(value = 1, message = "Giá dịch vụ phải lớn hơn 0")
    private Float price;
    private String description;
    @Column (columnDefinition = "boolean default true")
    private Boolean status = true;
}
