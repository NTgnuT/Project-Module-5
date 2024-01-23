package com.ra.repository;

import com.ra.model.entity.Booking;
import com.ra.model.entity.Product;
import com.ra.model.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findAllByRoomNoContainingIgnoreCase (Pageable pageable, String name);
    Page<Room> findAllByStatus (Pageable pageable, Boolean status);
    Boolean existsByRoomNo (String name);
}
