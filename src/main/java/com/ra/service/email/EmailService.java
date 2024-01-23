package com.ra.service.email;

import com.ra.model.dto.response.BookingResponseDTO;
import com.ra.model.entity.Booking;
import com.ra.model.entity.User;

public interface EmailService {
    String sendMail(User userLogin, BookingResponseDTO booking);
}
