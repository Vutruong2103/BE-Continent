package com.example.continent.application.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class EthnicGroupDto {
    private Long id;
    private String code;
    private String name;
    private List<Long> countryIds;
    private Set<String> countryNames;
    private Long continentIds;
    private String continentNames;
}
