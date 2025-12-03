package com.eCommerce.eCommerce.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDateTime dateCreated;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<CartItem> cartItems;


    public BigDecimal getTotalPrice() {
        return cartItems.stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public CartItem getItem(Long productId){
        return cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }
    public CartItem addItem(Product product){
        var cartItem = getItem(product.getId());

        if (cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(this);
            cartItems.add(cartItem);
        }
        return cartItem;
    }

    public void  removeItem(Product product){
        var cartItem = getItem(product.getId());
        if (cartItem != null){
//            if (cartItem.getQuantity() > 1){
//                cartItem.setQuantity(cartItem.getQuantity() - 1);
//            }
//            else {
            cartItem.setCart(null);
            cartItems.remove(cartItem);
//            }
        }

    }
    public void clearItem(){
        cartItems.clear();
    }
}