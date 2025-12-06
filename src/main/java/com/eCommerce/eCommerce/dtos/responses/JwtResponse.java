package com.eCommerce.eCommerce.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
@Data
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
}
