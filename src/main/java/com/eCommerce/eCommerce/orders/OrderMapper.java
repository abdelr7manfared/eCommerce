package com.eCommerce.eCommerce.orders;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto SingleToDto(Order order);

    List<OrderDto> toDto(List<Order> order);
    Set<OrderItemDto> itemToDto(Set<OrderItem> orderItem);



}
