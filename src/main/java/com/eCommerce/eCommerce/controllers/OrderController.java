package com.eCommerce.eCommerce.controllers;

import com.eCommerce.eCommerce.dtos.requests.CheckoutRequest;
import com.eCommerce.eCommerce.dtos.requests.OrderDto;
import com.eCommerce.eCommerce.dtos.responses.CheckoutResponse;
import com.eCommerce.eCommerce.services.OrderService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.net.Webhook;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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