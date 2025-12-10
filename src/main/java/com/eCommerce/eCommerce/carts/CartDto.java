package com.eCommerce.eCommerce.carts;

import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

@Data
public class CartDto {
    private UUID id;
    private Set<CartItemDto> items = new HashSet<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

}
