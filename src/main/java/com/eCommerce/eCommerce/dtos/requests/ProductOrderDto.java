package com.eCommerce.eCommerce.dtos.requests;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductOrderDto {
    private Long id;


    private String name;

    private String description;

    private BigDecimal price;

}
