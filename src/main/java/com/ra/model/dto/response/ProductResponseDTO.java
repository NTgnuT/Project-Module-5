package com.ra.model.dto.response;

import com.ra.model.entity.Product;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductResponseDTO {
    private Long id;
    private String productName;
    private Float price;
    private String description;
    private Boolean status;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.status = product.getStatus();
    }
}
