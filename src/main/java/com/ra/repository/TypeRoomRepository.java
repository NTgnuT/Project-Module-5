package com.ra.repository;

import com.ra.model.entity.Product;
import com.ra.model.entity.TypeRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRoomRepository extends JpaRepository<TypeRoom, Long> {
    Page<TypeRoom> findAllByTypeContainingIgnoreCase (Pageable pageable, String name);
    Page<TypeRoom> findAllByStatus (Pageable pageable, Boolean status);
    Boolean existsByType (String name);
}
