package com.example.continent.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class LanguageDto {
    @JsonIgnore
    private Long id;
    private String code;
    private String name;
    private List<Long> countryId;     // danh sách id của Country
    private Set<String> countryName;  // danh sách tên của Country
}
