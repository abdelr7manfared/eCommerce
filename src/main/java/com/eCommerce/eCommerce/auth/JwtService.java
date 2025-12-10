package com.eCommerce.eCommerce.auth;

import com.eCommerce.eCommerce.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

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
