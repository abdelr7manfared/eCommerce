package com.eCommerce.eCommerce.payments;

public class PaymentException extends RuntimeException {
    public PaymentException(String message){
        super(message);
    }
}
