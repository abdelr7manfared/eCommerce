package com.eCommerce.eCommerce.dtos;

import com.eCommerce.eCommerce.entities.Category;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
