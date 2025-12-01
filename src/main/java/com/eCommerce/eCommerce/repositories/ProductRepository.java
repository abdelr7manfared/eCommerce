package com.eCommerce.eCommerce.repositories;

import com.eCommerce.eCommerce.dtos.ProductDto;
import com.eCommerce.eCommerce.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategoryId(Long categoryId);

//    @EntityGraph(attributePaths = "category")
    @Query("SELECT p FROM Product p JOIN FETCH  p.category")
    List<Product> findAllWithCategory();
}
