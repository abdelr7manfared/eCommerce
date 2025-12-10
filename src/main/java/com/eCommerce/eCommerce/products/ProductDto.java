package com.eCommerce.eCommerce.products;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Long categoryId;

}
