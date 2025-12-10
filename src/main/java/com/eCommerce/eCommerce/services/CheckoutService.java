package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.dtos.responses.CheckoutResponse;
import com.eCommerce.eCommerce.entities.Order;
import com.eCommerce.eCommerce.entities.PaymentStatus;
import com.eCommerce.eCommerce.exceptions.CartNotFoundException;
import com.eCommerce.eCommerce.exceptions.PaymentException;
import com.eCommerce.eCommerce.repositories.CartRepostory;
import com.eCommerce.eCommerce.repositories.OrderRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartRepostory cartRepostory;
    private final AuthService authService;
    private final PaymentGateway paymentGateway;


    public CheckoutResponse checkout(UUID cartId)  {
        var cart = cartRepostory.findById(cartId).orElseThrow(CartNotFoundException::new);
        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new CartNotFoundException();
        }
        var user = authService.getCurrentUser();

        var order = Order.fromCart(cart, user);

        orderRepository.save(order);
        try {
            cartService.clearCartItem(cartId);
            var session = paymentGateway.createCheckoutSession(order);
            return new CheckoutResponse(order.getId(),session.getCheckoutUrl());
        }
        catch (PaymentException ex){
            orderRepository.delete(order);
            throw new PaymentException("");
        }



    }

    public void handleWebhook(WebhookRequest webhookRequest){
            // Take Webhook and return {orderId,paymentStatus}
            paymentGateway.
                    parseWebhookEvent(webhookRequest)
                    .ifPresent(paymentResult -> {
                    var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                    order.setPaymentStatus(paymentResult.getPaymentStatus());
                    orderRepository.save(order);
                    });

    }
}
