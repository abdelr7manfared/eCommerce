package com.eCommerce.eCommerce.mappers;

import com.eCommerce.eCommerce.dtos.ProductDto;
import com.eCommerce.eCommerce.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id" , target = "categoryId")
    ProductDto toDto(Product product);
}
