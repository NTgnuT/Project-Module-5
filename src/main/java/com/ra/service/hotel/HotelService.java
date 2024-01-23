package com.ra.service.hotel;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.HotelRequestDTO;
import com.ra.model.dto.response.HotelResponseDTO;
import com.ra.model.dto.response.HotelUserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelService {
    Page<HotelResponseDTO> findAll (Pageable pageable);
    Page<HotelResponseDTO> findAllByStatus (Pageable pageable);
    Page<HotelResponseDTO> searchByHotelName (Pageable pageable, String name);
    Page<HotelUserResponseDTO> searchByNameForUser (Pageable pageable, String name);
    HotelResponseDTO saveOrUpdate (HotelRequestDTO hotelRequestDTO) throws CustomException;
    HotelResponseDTO findById (Long id) throws CustomException;
    Boolean changeStatusById (Long id) throws CustomException;
}
