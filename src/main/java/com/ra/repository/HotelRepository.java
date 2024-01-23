package com.ra.repository;

import com.ra.model.entity.Hotel;
import com.ra.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Page<Hotel> findAllByHotelNameContainingIgnoreCase (Pageable pageable , String name);
    Page<Hotel> findAllByHotelNameContainingIgnoreCaseAndStatus (Pageable pageable, String name, Boolean status);
    Page<Hotel> findAllByStatus (Pageable pageable, Boolean status);
    Boolean existsByHotelName (String name);
}
