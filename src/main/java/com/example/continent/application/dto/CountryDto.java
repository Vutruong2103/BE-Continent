package com.example.continent.application.dto;

import lombok.Data;

@Data
public class CountryDto {
    private Long id;
    private String code;
    private String name;
    private Long continentId;// tham chiếu châu lục
}
