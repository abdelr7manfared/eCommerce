package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.dtos.requests.CartDto;
import com.eCommerce.eCommerce.dtos.requests.CartItemDto;
import com.eCommerce.eCommerce.entities.Cart;
import com.eCommerce.eCommerce.exceptions.CartNotFoundException;
import com.eCommerce.eCommerce.exceptions.ProductNotFoundException;
import com.eCommerce.eCommerce.mappers.CartMapper;
import com.eCommerce.eCommerce.repositories.CartRepostory;
import com.eCommerce.eCommerce.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepostory cartRepostory;
    private final ProductRepository productRepository;

    private final CartMapper cartMapper;
    public CartDto createCart() {
        var cart =  cartRepostory.save(new Cart() );
        return cartMapper.toDto(cart);
    }

    public CartItemDto addProductToCart(UUID uuid, Long productId) {
        var cart = cartRepostory.findById(uuid).orElse(null);
        if (cart == null){
            throw new CartNotFoundException();
        }
        var product = productRepository.findById(productId).orElse(null);
        if (product == null){
            throw new ProductNotFoundException();
        }
        var cartItem = cart.addItem(product);
        cartRepostory.save(cart);
        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepostory.findById(cartId).orElse(null);
        if (cart == null){
            throw new CartNotFoundException();
        }
        return cartMapper.toDto(cart);

    }

    public CartItemDto updateCartItem(UUID cartId, Long productId, Integer quantity) {
        var cart = cartRepostory.findById(cartId).orElse(null);
        if (cart == null){
            throw new CartNotFoundException();

        }
        var product = productRepository.findById(productId).orElse(null);
        if (product == null){
            throw new ProductNotFoundException();
        }

        var cartItem = cart.getItem(productId);
        if (cartItem == null){
            throw new ProductNotFoundException();
        }
        else {
            cartItem.setQuantity(quantity);
        }
        cartRepostory.save(cart);
        return cartMapper.toDto(cartItem);


    }

    public void removeCartItem(UUID cartId, Long productId) {
        var cart = cartRepostory.findById(cartId).orElse(null);
        if (cart == null){
            throw new CartNotFoundException();
        }
        var product = productRepository.findById(productId).orElse(null);
        if (product == null){
            throw new ProductNotFoundException();
        }
        cart.removeItem(product);
        cartRepostory.save(cart);
    }

    public void clearCartItem(UUID cartId) {
        var cart = cartRepostory.findById(cartId).orElse(null);
        if (cart == null){
            throw new CartNotFoundException();
        }
        cart.clearItem();
        cartRepostory.save(cart);
    }
}
