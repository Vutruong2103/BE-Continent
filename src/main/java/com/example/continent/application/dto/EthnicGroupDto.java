package com.example.continent.application.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class EthnicGroupDto {
    private Long id;
    private String code;
    private String name;
    private List<Long> countryId; // tham chiếu quốc gia
    private Set<String> countryName;
}
