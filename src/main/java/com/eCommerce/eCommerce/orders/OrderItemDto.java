package com.eCommerce.eCommerce.orders;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderItemDto {
    private ProductOrderDto product;
    private Integer quantity;
    private BigDecimal totalPrice;


}
