package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.entities.PaymentStatus;
import lombok.*;

@AllArgsConstructor
@Data
public class PaymentResult {
    private Long OrderId;
    private PaymentStatus paymentStatus;
}
