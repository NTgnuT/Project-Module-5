package com.ra.service.product;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.ProductRequestDTO;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductResponseDTO> findAll (Pageable pageable);
    Page<ProductResponseDTO> findAllByStatus (Pageable pageable);
    Page<ProductResponseDTO> searchByName (Pageable pageable, String name);
    ProductResponseDTO saveOrUpdate (ProductRequestDTO productRequestDTO) throws CustomException;
    ProductResponseDTO findById (Long id);
    void changeStatusById (Long id);
}
