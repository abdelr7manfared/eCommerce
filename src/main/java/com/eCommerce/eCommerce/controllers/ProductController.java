package com.eCommerce.eCommerce.controllers;

import com.eCommerce.eCommerce.dtos.responses.ProductDto;
import com.eCommerce.eCommerce.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name="Products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProduct(
            @RequestParam(required = false   , name = "categoryId ") Long categoryId
    )   {
        return productService.getAllProduct(categoryId);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto, UriComponentsBuilder builder){

        var productDtoResult = productService.createProduct(productDto);
        var uri = builder.path("/products/{id}").buildAndExpand(productDtoResult.getId()).toUri();
        return ResponseEntity.created(uri).body(productDtoResult);

    }
    @PutMapping("/{id}")
    public ProductDto updateProduct(
            @PathVariable Long id, @RequestBody ProductDto productDto){

        return productService.updateProduct(id,productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
