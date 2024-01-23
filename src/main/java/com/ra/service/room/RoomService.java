package com.ra.service.room;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.RoomRequestDTO;
import com.ra.model.dto.response.RoomResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomService {
    Page<RoomResponseDTO> findAll (Pageable pageable);
    Page<RoomResponseDTO> findAllByStatus (Pageable pageable);
    Page<RoomResponseDTO> searchByName (Pageable pageable, String name);
    RoomResponseDTO saveOrUpdate (RoomRequestDTO roomRequestDTO) throws CustomException;
    RoomResponseDTO findById (Long id);
    void changeStatusById (Long id) throws CustomException;
}
