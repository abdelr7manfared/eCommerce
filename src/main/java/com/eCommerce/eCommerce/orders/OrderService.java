package com.eCommerce.eCommerce.orders;

import com.eCommerce.eCommerce.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AuthService authService;





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
