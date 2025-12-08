package com.eCommerce.eCommerce.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddProductToOrderRequest {
    
    private Long productId;

}
