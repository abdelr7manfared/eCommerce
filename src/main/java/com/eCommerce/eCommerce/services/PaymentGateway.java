package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.entities.Order;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
}
