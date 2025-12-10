package com.eCommerce.eCommerce.dtos.requests;

import com.eCommerce.eCommerce.entities.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
@Data
public class OrderDto {

    private Long id;
    private PaymentStatus paymentStatus;
    private Date createdAt;
    private BigDecimal totalPrice;
    private Set<OrderItemDto> orderItem;
}
