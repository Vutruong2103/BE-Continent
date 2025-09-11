package com.example.continent.application.dto_;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class LanguageDto {
    @JsonIgnore
    private Long id;
    private String code;
    private String name;
    private List<Long> countryId;
    private Set<String> countryName;
}
