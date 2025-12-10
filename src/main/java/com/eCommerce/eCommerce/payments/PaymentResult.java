package com.eCommerce.eCommerce.payments;

import lombok.*;

@AllArgsConstructor
@Data
public class PaymentResult {
    private Long OrderId;
    private PaymentStatus paymentStatus;
}
