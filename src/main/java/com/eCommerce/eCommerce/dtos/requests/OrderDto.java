package com.eCommerce.eCommerce.dtos.requests;

import com.eCommerce.eCommerce.entities.OrderItem;
import com.eCommerce.eCommerce.entities.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
@Data
public class OrderDto {

    private Long id;
    private Status status;
    private Date createdAt;
    private BigDecimal totalPrice;
    private Set<OrderItemDto> orderItem;
}
