package com.eCommerce.eCommerce.controllers;

import com.eCommerce.eCommerce.dtos.requests.CheckoutRequest;
import com.eCommerce.eCommerce.dtos.requests.OrderDto;
import com.eCommerce.eCommerce.dtos.responses.CheckoutResponse;
import com.eCommerce.eCommerce.services.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponse> checkout(@Valid @RequestBody CheckoutRequest request) {
        var checkoutResponse = orderService.checkout(request.getCartId());
        return ResponseEntity.ok(checkoutResponse);
    }
    @GetMapping
    public ResponseEntity<List<OrderDto>> orders(){
        var orders = orderService.orders();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId){
        var order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

}