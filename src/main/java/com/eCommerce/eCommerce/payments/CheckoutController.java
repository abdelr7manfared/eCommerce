package com.eCommerce.eCommerce.payments;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
            @RequestHeader Map<String,String> headers,
            @RequestBody String payload) {
        checkoutService.handleWebhook(new WebhookRequest(headers,payload));

    }
}
