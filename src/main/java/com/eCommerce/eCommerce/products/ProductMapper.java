package com.eCommerce.eCommerce.products;

import com.eCommerce.eCommerce.orders.ProductOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id" , target = "categoryId")
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
    @Mapping(target = "id",ignore = true)
    void update(ProductDto productDto , @MappingTarget Product product);

    ProductOrderDto orderToDto(Product product);

}
