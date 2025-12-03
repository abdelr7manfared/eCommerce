package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.dtos.requests.AddItemToCartRequest;
import com.eCommerce.eCommerce.dtos.requests.CartDto;
import com.eCommerce.eCommerce.dtos.requests.CartItemDto;
import com.eCommerce.eCommerce.dtos.requests.UpdateCartItemRequest;
import com.eCommerce.eCommerce.entities.Cart;
import com.eCommerce.eCommerce.entities.CartItem;
import com.eCommerce.eCommerce.mappers.CartMapper;
import com.eCommerce.eCommerce.mappers.ProductMapper;
import com.eCommerce.eCommerce.repositories.CartRepostory;
import com.eCommerce.eCommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepostory cartRepostory;
    private final ProductRepository productRepository;

    private final CartMapper cartMapper;
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder builder) {
        var cart =  cartRepostory.save(new Cart() );
        var cartDto = cartMapper.toDto(cart);
        var uri =  builder.path("carts").buildAndExpand(cart.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }

    public ResponseEntity<CartItemDto> addProductToCart(UUID uuid, AddItemToCartRequest cartRequest) {
        var cart = cartRepostory.findById(uuid).orElse(null);
        if (cart == null){
            return ResponseEntity.notFound().build();
        }
        var product = productRepository.findById(cartRequest.getProductId()).orElse(null);
        if (product == null){
            return ResponseEntity.badRequest().build();
        }
        var cartItem = cart.getCartItems().stream()
                .filter(u -> u.getProduct().getId().equals(product.getId())).
                findFirst()
                .orElse(null);
        if (cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(cart);

            cart.getCartItems().add(cartItem);
        }
            cartRepostory.save(cart);
        var cartItemDto = cartMapper.toDto(cartItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);


    }

    public ResponseEntity<CartDto> getCart(UUID cartId) {
        var cart = cartRepostory.findById(cartId).orElse(null);
        if (cart == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cartMapper.toDto(cart));

    }

    public ResponseEntity<?> updateCartItem(UUID cartId, Long productId, UpdateCartItemRequest cartItemRequest) {
        var cart = cartRepostory.findById(cartId).orElse(null);
        if (cart == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("Error","Cart not found")
            );
        }
        var product = productRepository.findById(productId).orElse(null);
        if (product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("Error","Product not found")
            );
        }

        var cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);
        if (cartItem == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("Error","Product not found in this cart")
            );        }
        else {
            cartItem.setQuantity(cartItemRequest.getQuantity());
        }
        cartRepostory.save(cart);

        return ResponseEntity.ok(cartMapper.toDto(cartItem));


    }
}
