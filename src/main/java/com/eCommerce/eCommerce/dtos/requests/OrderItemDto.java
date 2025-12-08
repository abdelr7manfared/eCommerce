package com.eCommerce.eCommerce.dtos.requests;

import com.eCommerce.eCommerce.dtos.responses.ProductDto;
import com.eCommerce.eCommerce.entities.Product;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class OrderItemDto {
    private ProductOrderDto product;
    private Integer quantity;
    private BigDecimal totalPrice;


}
