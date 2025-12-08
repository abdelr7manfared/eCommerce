package com.eCommerce.eCommerce.mappers;

import com.eCommerce.eCommerce.dtos.requests.OrderDto;
import com.eCommerce.eCommerce.dtos.requests.OrderItemDto;
import com.eCommerce.eCommerce.dtos.requests.ProductOrderDto;
import com.eCommerce.eCommerce.entities.Order;
import com.eCommerce.eCommerce.entities.OrderItem;
import com.eCommerce.eCommerce.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto SingleToDto(Order order);

    List<OrderDto> toDto(List<Order> order);
    Set<OrderItemDto> itemToDto(Set<OrderItem> orderItem);



}
