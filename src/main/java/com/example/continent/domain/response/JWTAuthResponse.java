package com.example.continent.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JWTAuthResponse {
    private String token;
}
