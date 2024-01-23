package com.ra.model.dto.response;

import com.ra.model.entity.TypeRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TypeRoomResponseDTO {
    private Long id;
    private String type;
    private Boolean status = true;

    public TypeRoomResponseDTO(TypeRoom typeRoom) {
        this.id = typeRoom.getId();
        this.type = typeRoom.getType();
        this.status = typeRoom.getStatus();
    }
}
