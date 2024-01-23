package com.ra.model.dto.response;

import com.ra.model.entity.Hotel;
import com.ra.model.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HotelUserResponseDTO {
    private Long id;
    private String hotelName;
    private String image;
    private String address;
    private String description;
    private Boolean status;
    private Set<String> rooms;

    public HotelUserResponseDTO(Hotel hotel) {
        this.id = hotel.getId();
        this.hotelName = hotel.getHotelName();
        this.image = hotel.getImage();
        this.address = hotel.getAddress();
        this.description = hotel.getDescription();
        this.status = hotel.getStatus();
        this.rooms = hotel.getRooms().stream()
                .filter(room -> Boolean.TRUE.equals(room.getStatus()))
                .map(Room::getRoomNo).collect(Collectors.toSet());
    }
}
