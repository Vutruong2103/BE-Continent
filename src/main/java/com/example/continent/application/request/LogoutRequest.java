package com.example.continent.application.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LogoutRequest {
    private String token;
}
