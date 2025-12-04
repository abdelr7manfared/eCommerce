package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.dtos.responses.ProductDto;
import com.eCommerce.eCommerce.entities.Product;
import com.eCommerce.eCommerce.exceptions.CategoryNotFoundException;
import com.eCommerce.eCommerce.exceptions.ProductNotFoundException;
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

    public ProductDto getProductById(Long id) {
        var product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        return productMapper.toDto(product);
    }

    public ProductDto createProduct(ProductDto productDto) {
        categoryRepository.findById(productDto.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        var product = productMapper.toEntity(productDto);
        productRepository.save(product);
        productDto.setId(product.getId());
        return productDto;
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        var category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(CategoryNotFoundException::new);

        var product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        productMapper.update(productDto,product);

        product.setCategory(category);

        productRepository.save(product);

        return productMapper.toDto(product);
    }

    public void deleteProduct(Long id) {

        var product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

        productRepository.delete(product);
    }
}
