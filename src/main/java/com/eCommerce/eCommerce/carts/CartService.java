package com.eCommerce.eCommerce.carts;

import com.eCommerce.eCommerce.products.ProductNotFoundException;
import com.eCommerce.eCommerce.products.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        var cart = cartRepostory.findById(uuid).orElseThrow(CartNotFoundException::new);

        var product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        var cartItem = cart.addItem(product);

        cartRepostory.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepostory.findById(cartId).orElseThrow(CartNotFoundException::new);

         return cartMapper.toDto(cart);

    }

    public CartItemDto updateCartItem(UUID cartId, Long productId, Integer quantity) {
        var cart = cartRepostory.findById(cartId).orElseThrow(CartNotFoundException::new);

        productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

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
        var cart = cartRepostory.findById(cartId).orElseThrow(CartNotFoundException::new);

        var product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);


        cart.removeItem(product);
        cartRepostory.save(cart);
    }

    public void clearCartItem(UUID cartId) {
        var cart = cartRepostory.findById(cartId).orElseThrow(CartNotFoundException::new);

        cart.clearItem();
        cartRepostory.save(cart);
    }
}
