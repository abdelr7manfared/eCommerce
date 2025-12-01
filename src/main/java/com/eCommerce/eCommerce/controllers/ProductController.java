package com.eCommerce.eCommerce.controllers;

import com.eCommerce.eCommerce.dtos.ProductDto;
import com.eCommerce.eCommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
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
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

}
