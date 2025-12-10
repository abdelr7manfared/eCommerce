package com.eCommerce.eCommerce.orders;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;


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