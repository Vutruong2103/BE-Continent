package com.example.continent.application.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * Dto là cái để trả cho clinent
 */
@Data
public class ContinentDto {
    private Long id;
    private String code;
    private String name;
    private List<Long> countryIds;
    private Set<String> countryNames;

    private List<Long> ethnicGroupIds;
    private Set<String> ethnicGroupNames;
}
