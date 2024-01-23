package com.ra.model.dto.response;

import com.ra.model.entity.Room;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomResponseDTO {
    private Long id;
    private String roomNo;
    private String image;
    private Float price;
    private String description;
    private Boolean status;
    private String hotelName;
    private String typeRoomName;

    public RoomResponseDTO(Room room) {
        this.id = room.getId();
        this.roomNo = room.getRoomNo();
        this.image = room.getImage();
        this.price = room.getPrice();
        this.description = room.getDescription();
        this.status = room.getStatus();
        this.hotelName = room.getHotel().getHotelName();
        this.typeRoomName = room.getTypeRoom().getType();
    }
}
