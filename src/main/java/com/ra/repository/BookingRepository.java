package com.ra.repository;

import com.ra.model.entity.Booking;
import com.ra.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findAllByUser (Pageable pageable, User user);
    Integer countAllByCheckInBetween (Date start, Date end);
}
