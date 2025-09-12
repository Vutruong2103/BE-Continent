package com.example.continent.application.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CountryDto {
    private Long id;
    private String code;
    private String name;
    private Long continentId;
    private String continentName;
    private List<Long> languageIds;
    private Set<String> languageNames;
    private List<Long> ethnicGroupIds;
    private Set<String> ethnicGroupNames;
}
