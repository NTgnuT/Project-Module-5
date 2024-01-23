package com.ra.service.booking;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.BookingRequestDTO;
import com.ra.model.dto.response.BookingResponseDTO;
import com.ra.model.entity.Booking;
import com.ra.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    Page<BookingResponseDTO> findAll (Pageable pageable);
    Page<BookingResponseDTO> searchByUser (Pageable pageable, Long id) throws CustomException;
    BookingResponseDTO save (BookingRequestDTO bookingRequestDTO, User userLogin) throws CustomException;
    Booking findById (Long id) throws CustomException;
    void changeStatus (Long id, int status) throws CustomException;
    Integer countBooking (Integer year);
}
