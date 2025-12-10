package com.eCommerce.eCommerce.payments;

import com.eCommerce.eCommerce.orders.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult>  parseWebhookEvent(WebhookRequest request);
}
