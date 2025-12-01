package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.dtos.ProductDto;
import com.eCommerce.eCommerce.entities.Product;
import com.eCommerce.eCommerce.mappers.ProductMapper;
import com.eCommerce.eCommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    public List<ProductDto> getAllProduct(Long categoryId) {
        List<Product> products;
        if (categoryId == null){
            products = productRepository.findAllWithCategory();
        }
        else {
            products = productRepository.findByCategoryId(categoryId);
        }

        return products
                .stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ResponseEntity<ProductDto> getProductById(Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDto(product));
    }
}
