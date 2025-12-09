package com.eCommerce.eCommerce.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckoutResponse {
    private Long orderId;
    private String url;

}
