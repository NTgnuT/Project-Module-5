package com.ra.service.product;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.ProductRequestDTO;
import com.ra.model.dto.response.ProductResponseDTO;
import com.ra.model.entity.Product;
import com.ra.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(ProductResponseDTO::new);
    }

    @Override
    public Page<ProductResponseDTO> findAllByStatus(Pageable pageable) {
        Page<Product> productPage = productRepository.findAllByStatus(pageable, true);
        return productPage.map(ProductResponseDTO::new);
    }

    @Override
    public Page<ProductResponseDTO> searchByName(Pageable pageable, String name) {
        Page<Product> productPage = productRepository.findAllByProductNameContainingIgnoreCase(pageable, name);
        return productPage.map(ProductResponseDTO::new);
    }

    @Override
    public ProductResponseDTO saveOrUpdate (ProductRequestDTO productRequestDTO) throws CustomException {
        Product product;

        if (productRequestDTO.getId() == null) {
            if (productRepository.existsByProductName(productRequestDTO.getProductName())) {
                throw new CustomException("Dịch vụ này đã tồn tại !!!");
            }
            product = new Product();
            product.setProductName(productRequestDTO.getProductName());
            product.setDescription(productRequestDTO.getDescription());
            product.setPrice(productRequestDTO.getPrice());
            product.setStatus(productRequestDTO.getStatus());
        } else {
            product = productRepository.findById(productRequestDTO.getId())
                    .orElseThrow(() -> new CustomException("Không tìm thấy dịch vụ này!!!"));
            if (!product.getProductName().equals(productRequestDTO.getProductName())) {
                if (productRepository.existsByProductName(productRequestDTO.getProductName())) {
                    throw new CustomException("Dịch vụ này đã tồn tại !!!");
                }
                product.setProductName(productRequestDTO.getProductName());
            }
            product.setDescription(productRequestDTO.getDescription());
            product.setPrice(productRequestDTO.getPrice());
            product.setStatus(productRequestDTO.getStatus());
        }

        productRepository.save(product);

        return new ProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ProductResponseDTO::new).orElse(null);
    }

    @Override
    public void changeStatusById(Long id) {
//        ProductResponseDTO productResponseDTO = findById(id);
        productRepository.changeStatusById(id);
    }
}
