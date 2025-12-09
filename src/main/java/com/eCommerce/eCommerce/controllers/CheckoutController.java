package com.eCommerce.eCommerce.controllers;

import com.eCommerce.eCommerce.dtos.requests.CheckoutRequest;
import com.eCommerce.eCommerce.dtos.responses.CheckoutResponse;
import com.eCommerce.eCommerce.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
@AllArgsConstructor
public class CheckoutController {
    private final CheckoutService checkoutService;
    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout(@Valid @RequestBody CheckoutRequest request) {
        var checkoutResponse = checkoutService.checkout(request.getCartId());
        return ResponseEntity.ok(checkoutResponse);
    }
    @PostMapping("/webhook")
    public void handleWebhook(
            @RequestHeader("Stripe-Signature") String signature,
            @RequestBody String payload) {
        checkoutService.handleWebhook(signature,payload);

    }
}
