package com.eCommerce.eCommerce.auth;

import com.eCommerce.eCommerce.users.User;
import com.eCommerce.eCommerce.users.EmailNotFoundException;
import com.eCommerce.eCommerce.users.UserMapper;
import com.eCommerce.eCommerce.users.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

        return new TokenResponse(accesstoken.toString(),refreshToken.toString());
    }

    public boolean validate(String token) {
        var jwt = jwtService.parseToken(token);
        return (jwt != null && !jwt.isExpired(token));
    }

    public JwtResponse refresh(String refreshToken) {
        var jwt = jwtService.parseToken(refreshToken);
        if (jwt == null || jwt.isExpired(refreshToken)){
            throw new TokenNotValid();
        }
        var userId = jwt.getUserID(refreshToken);
        var user = userRepository.findById(userId).orElseThrow();
        var accessToken = jwtService.genrateAcessToken(user);
        return new JwtResponse(accessToken.toString());

    }
    public User getCurrentUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long)authentication.getPrincipal();
        return userRepository.findById(userId).orElse(null);
    }
}
