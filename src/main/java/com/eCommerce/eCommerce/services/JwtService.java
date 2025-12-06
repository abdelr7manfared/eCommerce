package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.config.JwtConfig;
import com.eCommerce.eCommerce.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;
        public String genrateAcessToken(User  user) {
            return genrateToken(user, jwtConfig.getAccessTokenExpiration());
        }

        public String genrateRefreshToken(User  user) {
            return genrateToken(user,jwtConfig.getRefreshTokenExpiration());
        }
        public String genrateToken(User  user,int tokenExpiration){
        return Jwts.builder()
                .claim("name",user.getName())
                .claim("email",user.getEmail())
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * tokenExpiration))
                .signWith(jwtConfig.getSecret())
                .compact();
    }
    public boolean validateToken(String token){
        try {
            var claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        }
        catch (Exception ex){
            return false;
        }

    }
    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecret())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getClaims(token).getSubject());
    }
}
