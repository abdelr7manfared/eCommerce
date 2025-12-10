package com.eCommerce.eCommerce.exceptions;

public class PaymentException extends RuntimeException {
    public PaymentException(String message){
        super(message);
    }
}
