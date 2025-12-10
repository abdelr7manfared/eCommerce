package com.eCommerce.eCommerce.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<Boolean> existsByEmail(String email);

   Optional<User> findByEmail(@NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email);
}
