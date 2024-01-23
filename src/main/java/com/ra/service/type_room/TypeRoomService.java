package com.ra.service.type_room;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.TypeRoomRequestDTO;
import com.ra.model.dto.response.TypeRoomResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TypeRoomService {
    Page<TypeRoomResponseDTO> findAll (Pageable pageable);
    Page<TypeRoomResponseDTO> findAllByStatus (Pageable pageable);
    Page<TypeRoomResponseDTO> searchByName (Pageable pageable, String name);
    TypeRoomResponseDTO saveOrUpdate (TypeRoomRequestDTO typeRoomRequestDTO) throws CustomException;
    TypeRoomResponseDTO findById (Long id);
    void changeStatusById (Long id) throws CustomException;
}
