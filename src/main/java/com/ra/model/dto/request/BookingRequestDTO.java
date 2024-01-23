package com.ra.model.dto.request;

import com.ra.model.entity.Product;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingRequestDTO {
    private Long id;
    @Min(value = 1, message = "Số ngày đặt phải lớn hơn 0")

    private Integer days;
    private String note;
    @Future (message = "Ngày đặt phòng phải là ngày trong tương lai")
    @NotNull (message = "Ngày check in không được trống")
    private Date checkIn;
//    private Long userId;
    @NotNull (message = "Hãy chọn phòng bạn muốn đặt")
    private Long roomId;
    private Set<Long> productId;
    @Column(nullable = false, columnDefinition = "int default 0")
    private int status = 0;
}
