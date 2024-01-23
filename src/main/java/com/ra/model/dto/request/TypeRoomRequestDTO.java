package com.ra.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TypeRoomRequestDTO {
    private Long id;
    @Column(unique = true)
    @NotBlank(message = "Kiểu phòng này đã tồn tại!!!")
    private String type;
    @Column(columnDefinition = "boolean default true")
    private Boolean status = true;
}
