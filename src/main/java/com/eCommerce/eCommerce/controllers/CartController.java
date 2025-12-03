package com.eCommerce.eCommerce.controllers;

import com.eCommerce.eCommerce.dtos.requests.AddItemToCartRequest;
import com.eCommerce.eCommerce.dtos.requests.CartDto;
import com.eCommerce.eCommerce.dtos.requests.CartItemDto;
import com.eCommerce.eCommerce.dtos.requests.UpdateCartItemRequest;
import com.eCommerce.eCommerce.entities.CartItem;
import com.eCommerce.eCommerce.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder builder){
        return cartService.createCart(builder);
    }
    @PostMapping("/{uuid}/items")
    public ResponseEntity<CartItemDto> addProductToCart(
            @PathVariable UUID uuid ,
            @RequestBody AddItemToCartRequest cartRequest
            ){
        return cartService.addProductToCart(uuid,cartRequest);
    }
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId){
        return cartService.getCart(cartId);
    }
    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> updateCartItem(@PathVariable UUID cartId ,
                          @PathVariable Long productId   ,
                          @Valid @RequestBody UpdateCartItemRequest cartItemRequest
      ){
        return cartService.updateCartItem(cartId,productId,cartItemRequest);
    }
}
