package com.eCommerce.eCommerce.mappers;

import com.eCommerce.eCommerce.dtos.requests.CartDto;
import com.eCommerce.eCommerce.dtos.requests.CartItemDto;
import com.eCommerce.eCommerce.entities.Cart;
import com.eCommerce.eCommerce.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "items",source = "cartItems")
    @Mapping(target = "totalPrice",expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);
    @Mapping(target = "totalPrice" , expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);

}
