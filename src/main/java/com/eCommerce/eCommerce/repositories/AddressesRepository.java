package com.eCommerce.eCommerce.repositories;

import com.eCommerce.eCommerce.entities.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressesRepository extends JpaRepository<Addresses,Long> {
}
