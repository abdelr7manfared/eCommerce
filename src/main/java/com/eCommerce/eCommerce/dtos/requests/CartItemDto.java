package com.eCommerce.eCommerce.dtos.requests;

import com.eCommerce.eCommerce.dtos.responses.CartProductDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private CartProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice;

}
