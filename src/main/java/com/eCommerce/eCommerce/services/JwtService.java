package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.config.JwtConfig;
import com.eCommerce.eCommerce.entities.Role;
import com.eCommerce.eCommerce.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;
        public Jwt genrateAcessToken(User  user) {
            return genrateToken(user, jwtConfig.getAccessTokenExpiration());
        }

        public Jwt genrateRefreshToken(User  user) {
            return genrateToken(user,jwtConfig.getRefreshTokenExpiration());
        }
        public Jwt genrateToken(User  user,int tokenExpiration){
            var claims = Jwts.claims()
                    .subject(user.getId().toString())
                    .add("name",user.getName())
                    .add("email",user.getEmail())
                    .add("role",user.getRole())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000L * tokenExpiration))
                    .build();
            return new Jwt(claims, jwtConfig.getSecret());
     }
    public Jwt parseToken(String token){
            try {
                var claims = getClaims(token);
                return new Jwt(claims,jwtConfig.getSecret());

            }catch (JwtException exception){
                return null;
            }
    }
    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecret())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
