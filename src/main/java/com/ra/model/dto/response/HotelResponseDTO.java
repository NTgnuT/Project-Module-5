package com.ra.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ra.model.entity.Hotel;
import com.ra.model.entity.Room;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HotelResponseDTO {
    private Long id;
    private String hotelName;
    private String image;
    private String address;
    private String description;
    private Boolean status;
//    private List<Room> rooms;

    public HotelResponseDTO(Hotel hotel) {
        this.id = hotel.getId();
        this.hotelName = hotel.getHotelName();
        this.image = hotel.getImage();
        this.address = hotel.getAddress();
        this.description = hotel.getDescription();
        this.status = hotel.getStatus();
//        this.rooms = hotel.getRooms();
    }
}
