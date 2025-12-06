package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.dtos.requests.LoginRequest;
import com.eCommerce.eCommerce.dtos.responses.JwtResponse;
import com.eCommerce.eCommerce.dtos.responses.TokenResponse;
import com.eCommerce.eCommerce.dtos.responses.UserDto;
import com.eCommerce.eCommerce.exceptions.EmailNotFoundException;
import com.eCommerce.eCommerce.exceptions.TokenNotValid;
import com.eCommerce.eCommerce.mappers.UserMapper;
import com.eCommerce.eCommerce.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public TokenResponse login(@Valid LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        var user = userRepository .findByEmail(request.getEmail()).orElseThrow(EmailNotFoundException::new);
        var accesstoken = jwtService.genrateAcessToken(user);
        var refreshToken = jwtService.genrateRefreshToken(user);

        return new TokenResponse(accesstoken,refreshToken);
    }

    public boolean validate(String token) {
        return jwtService.validateToken(token);
    }

    public JwtResponse refresh(String refreshToken) {
        if (!jwtService.validateToken(refreshToken)){
            throw new TokenNotValid();
        }
        var userId = jwtService.getUserIdFromToken(refreshToken);
        var user = userRepository.findById(userId).orElseThrow();
        var accessToken = jwtService.genrateAcessToken(user);
        return new JwtResponse(accessToken);

    }
}
