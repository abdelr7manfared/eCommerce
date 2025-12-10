package com.eCommerce.eCommerce.payments;

import lombok.*;

import java.util.Map;
@AllArgsConstructor
@Data
public class WebhookRequest {
    private Map<String,String> headers;
    private String payload;
}
