package com.eCommerce.eCommerce.auth;

import com.eCommerce.eCommerce.users.UserDto;
import com.eCommerce.eCommerce.users.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    private final JwtConfig jwtConfig;
    @PostMapping("/login")
        public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request,
                                                 HttpServletResponse response){
            var tokens = authService.login(request);

            var cookie = new Cookie("refreshToken",tokens.getRefreshToken());
            cookie.setHttpOnly(true);
            cookie.setPath("/auth");
            cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
            cookie.setSecure(true);
            response.addCookie(cookie);


            return ResponseEntity.ok(new JwtResponse(tokens.getAccessToken()));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(name = "refreshToken") String refreshToken){
        var jwtResponse = authService.refresh(refreshToken);
        return ResponseEntity.ok(jwtResponse);
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
