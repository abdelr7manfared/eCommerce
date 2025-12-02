package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.dtos.responses.ProductDto;
import com.eCommerce.eCommerce.entities.Product;
import com.eCommerce.eCommerce.mappers.ProductMapper;
import com.eCommerce.eCommerce.repositories.CategoryRepository;
import com.eCommerce.eCommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
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

    public ResponseEntity<ProductDto> createProduct(ProductDto productDto, UriComponentsBuilder builder) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null){
            return ResponseEntity.badRequest().build();
        }
        var product = productMapper.toEntity(productDto);
        productRepository.save(product);
        productDto.setId(product.getId());
        var uri = builder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    public ResponseEntity<ProductDto> updateProduct(Long id, ProductDto productDto) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null){
            return ResponseEntity.badRequest().build();
        }
        var product = productRepository.findById(id).orElse(null);
        if (product == null){
            return ResponseEntity.notFound().build();
        }
        productMapper.update(productDto,product);
        product.setCategory(category);
        productRepository.save(product);

        return ResponseEntity.ok(productMapper.toDto(product));
    }

    public ResponseEntity<Void> deleteProduct(Long id) {

        var product = productRepository.findById(id).orElse(null);
        if (product == null){
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }
}
