package com.eCommerce.eCommerce.controllers;

import com.eCommerce.eCommerce.dtos.requests.LoginRequest;
import com.eCommerce.eCommerce.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
        public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request){
            authService.login(request);
            return ResponseEntity.ok().build();
    }
}
