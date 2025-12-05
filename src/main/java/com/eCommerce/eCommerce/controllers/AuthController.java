package com.eCommerce.eCommerce.controllers;

import com.eCommerce.eCommerce.dtos.requests.LoginRequest;
import com.eCommerce.eCommerce.dtos.responses.JwtResponse;
import com.eCommerce.eCommerce.dtos.responses.UserDto;
import com.eCommerce.eCommerce.services.AuthService;
import com.eCommerce.eCommerce.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    @PostMapping("/login")
        public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request){
            var jwtResponse = authService.login(request);
            return ResponseEntity.ok().body(jwtResponse);
    }
    @PostMapping("/validate")
    public boolean validate(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ","");
        return authService.validate(token);
    }
    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long)authentication.getPrincipal();
        var userDto =  userService.getUser(userId);
        return ResponseEntity.ok(userDto);
    }
}
