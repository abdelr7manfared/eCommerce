package com.eCommerce.eCommerce.repositories;

import com.eCommerce.eCommerce.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CartRepostory extends JpaRepository<Cart, UUID> {
}
