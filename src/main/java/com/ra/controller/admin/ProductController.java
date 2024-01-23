package com.ra.controller.admin;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.ProductRequestDTO;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.model.entity.Product;
import com.ra.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponseDTO>> getAll (@RequestParam(name = "page", defaultValue = "0") int page,
                                                            @RequestParam(name = "size", defaultValue = "5") int size,
                                                            @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                            @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<ProductResponseDTO> productResponseDTOS = productService.findAll(pageable);
        return new ResponseEntity<>(productResponseDTOS, HttpStatus.OK);
    }
    @GetMapping("/products/search")
    public ResponseEntity<Page<ProductResponseDTO>> search (@RequestParam(name = "page", defaultValue = "0") int page,
                                                                @RequestParam(name = "size", defaultValue = "5") int size,
                                                                @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                                @RequestParam(name = "order", defaultValue = "desc") String order,
                                                                @RequestParam(name = "search") String search) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        }
        Page<ProductResponseDTO> productResponseDTOS = productService.searchByName(pageable, search);
        return new ResponseEntity<>(productResponseDTOS, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<?> create (@Valid @RequestBody ProductRequestDTO productRequestDTO) throws CustomException {
        ProductResponseDTO product = productService.saveOrUpdate(productRequestDTO);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct (@Valid @PathVariable("id") Long id,
                                                  @RequestBody ProductRequestDTO productRequestDTO ) throws CustomException {
        ProductResponseDTO product = productService.findById(id);

        productRequestDTO.setId(product.getId());
        ProductResponseDTO productUpdate = productService.saveOrUpdate(productRequestDTO);

        return new ResponseEntity<>(productUpdate, HttpStatus.OK);
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<?> changeStatus (@PathVariable("id") Long id) {
        productService.changeStatusById(id);
        ProductResponseDTO product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
