package com.eCommerce.eCommerce.controllers;

import com.eCommerce.eCommerce.dtos.requests.AddItemToCartRequest;
import com.eCommerce.eCommerce.dtos.requests.CartDto;
import com.eCommerce.eCommerce.dtos.requests.CartItemDto;
import com.eCommerce.eCommerce.dtos.requests.UpdateQuantityRequest;
import com.eCommerce.eCommerce.services.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
@Tag(name="Carts")
public class CartController {
    private final CartService cartService;
    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder builder){
        var cartDto = cartService.createCart();
        var uri = builder.path("/carts/{}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }
    @PostMapping("/{uuid}/items")
    public ResponseEntity<CartItemDto> addProductToCart(
            @PathVariable UUID uuid ,
            @RequestBody AddItemToCartRequest cartRequest){
        var cartItemDto =  cartService.addProductToCart(uuid,cartRequest.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);

    }
    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable UUID cartId){
        return cartService.getCart(cartId);
    }
    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateCartItem(@PathVariable UUID cartId ,
                          @PathVariable Long productId   ,
                          @Valid @RequestBody UpdateQuantityRequest cartItemRequest){
        return cartService.updateCartItem(cartId,productId,cartItemRequest.getQuantity());
    }
    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> removeCartItem(@PathVariable UUID cartId ,
                                            @PathVariable Long productId){
        cartService.removeCartItem(cartId,productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCartItem(@PathVariable UUID cartId){
        cartService.clearCartItem(cartId);
        return ResponseEntity.noContent().build();

    }
}
