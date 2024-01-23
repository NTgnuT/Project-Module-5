package com.ra.model.dto.response;

import com.ra.model.entity.Booking;
import com.ra.model.entity.Product;
import com.ra.model.entity.Room;
import lombok.*;

import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookingResponseDTO {
    private Long id;
    private Integer days;
    private String note;
    private Date checkIn;
    private String userName;
    private String email;
    private String phone;
    private Room room;
    private String status;
    private Set<String> products;
    private Float total;

    public BookingResponseDTO(Booking booking) {
        this.id = booking.getId();
        this.days = booking.getDays();
        this.note = booking.getNote();
        this.checkIn = booking.getCheckIn();
        this.userName = booking.getUser().getUserName();
        this.email = booking.getUser().getEmail();
        this.phone = booking.getUser().getPhoneNumber();
        this.room = booking.getRoom();
        this.status = booking.getStatus() == 0 ? "Chờ xác nhận" : booking.getStatus() == 1 ? "Đã xác nhận" : "Đã hủy";
        this.products = booking.getProducts().stream().map(Product::getProductName).collect(Collectors.toSet());
        float totalProduct = 0;
        for (Product product : booking.getProducts()) {
            totalProduct += product.getPrice();
        }

        total = booking.getDays() * booking.getRoom().getPrice() + totalProduct;
    }
}
