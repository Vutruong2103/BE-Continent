package com.example.continent.application.dto_;

import lombok.Data;

@Data
public class CountryDto {
    private Long id;
    private String code;
    private String name;
    private Long continentId;
}
