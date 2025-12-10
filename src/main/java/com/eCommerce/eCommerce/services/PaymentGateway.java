package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.entities.Order;
import com.stripe.exception.SignatureVerificationException;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult>  parseWebhookEvent(WebhookRequest request);
}
