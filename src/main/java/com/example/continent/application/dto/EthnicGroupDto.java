package com.example.continent.application.dto;

import lombok.Data;

@Data
public class EthnicGroupDto {
    private Long id;
    private String code;
    private String name;
    private Long countryId; // tham chiếu quốc gia
}
