package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.dtos.responses.CheckoutResponse;
import com.eCommerce.eCommerce.entities.Order;
import com.eCommerce.eCommerce.entities.OrderItem;
import com.eCommerce.eCommerce.exceptions.PaymentException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class StripePaymentGateway implements PaymentGateway{
    @Value("${websiteUrl}")
    private String websiteUrl;
    @Override
    public CheckoutSession createCheckoutSession(Order order) {
    try {
        var builder = SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(websiteUrl + "/success")
                        .setCancelUrl(websiteUrl + "/cancel")
                        .putMetadata("orderId",order.getId().toString());

        order.getOrderItem().forEach(orderItem -> {
            var lineItem = getLineItem(orderItem);
            builder.addLineItem(lineItem);
        });

        var session = Session.create(builder.build());
        return new CheckoutSession(session.getUrl());

        }
        catch (StripeException ex){
            throw new PaymentException();
        }
    }

    private static SessionCreateParams.LineItem getLineItem(OrderItem orderItem) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(orderItem.getQuantity()))
                .setPriceData(priceData(orderItem))
                .build();
    }

    private static SessionCreateParams.LineItem.PriceData priceData(OrderItem orderItem) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(orderItem.getUnitPrice()
                        .multiply(BigDecimal.valueOf(100)))
                .setProductData(getProduct(orderItem))
                .build();
    }

    private static SessionCreateParams.LineItem.PriceData.ProductData getProduct(OrderItem orderItem) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setDescription(orderItem.getProduct().getDescription())
                .setName(orderItem.getProduct().getName())
                .build();
    }
}
