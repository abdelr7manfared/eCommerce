package com.eCommerce.eCommerce.services;

import com.eCommerce.eCommerce.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

public class Jwt {
    private final SecretKey secreKey;
    private final Claims claims;
    public Jwt(Claims claims , SecretKey secreKey){
        this.claims = claims;
        this.secreKey = secreKey;
    }

    public boolean isExpired(String token){
        return claims.getExpiration().before(new Date());
    }
    public Long getUserID(String token){
        return Long.valueOf(claims.getSubject());
    }
    public Role getRole(String token){
        return Role.valueOf(claims.get("role",String.class));
    }
    public String toString(){
      return   Jwts.builder().claims(claims).signWith(secreKey).compact();
    }


}
