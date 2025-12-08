package com.eCommerce.eCommerce.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;
@Data
public class CheckoutRequest {
    @NotNull
    private UUID cartId;
}
