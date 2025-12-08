package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.dtos.requests.OrderDto;
import com.eCommerce.eCommerce.dtos.responses.CheckoutResponse;
import com.eCommerce.eCommerce.entities.Order;
import com.eCommerce.eCommerce.entities.OrderItem;
import com.eCommerce.eCommerce.entities.Status;
import com.eCommerce.eCommerce.exceptions.CartNotFoundException;
import com.eCommerce.eCommerce.exceptions.OrderNotFoundException;
import com.eCommerce.eCommerce.mappers.OrderMapper;
import com.eCommerce.eCommerce.repositories.CartRepostory;
import com.eCommerce.eCommerce.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final CartRepostory cartRepostory;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AuthService authService;


    public CheckoutResponse checkout(UUID cartId) {
        var cart = cartRepostory.findById(cartId).orElseThrow(CartNotFoundException::new);
        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()){
            throw new CartNotFoundException();
        }
        var user = authService.getCurrentUser();

        var order = Order.fromCart(cart,user);

        orderRepository.save(order);

        cartService.clearCartItem(cartId);

        return new CheckoutResponse(order.getId());

    }

    public List<OrderDto> orders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.findAllBycustomer(user);
        var ordersDto = orderMapper.toDto(orders);
        System.out.println(ordersDto);
        return ordersDto;
    }

    public OrderDto getOrder(Long orderId) {
        var order = orderRepository.getOrderWithItems(orderId).orElseThrow(OrderNotFoundException::new);
        var user = authService.getCurrentUser();
        if (!order.isPlacedBy(user)){
            throw new AccessDeniedException("You dont have access for that ");
        }
    return orderMapper.SingleToDto(order);


    }
}
