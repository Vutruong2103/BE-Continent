package com.example.continent.application.mapper_;

import com.example.continent.application.dto_.LanguageDto;
import com.example.continent.domain.model_.Country;
import com.example.continent.domain.model_.Language;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @TODO : Cái này vẫn không có update nè em .
*/
@Mapper(componentModel = "spring")
public interface LanguageMapper {
    @Mapping(target = "countryId", source = "countries", qualifiedByName = "mappingRuleCountryId")
    @Mapping(target = "countryName", source = "countries", qualifiedByName = "mappingRuleCountryName")
    LanguageDto toDto(Language language);

    @Mapping(target = "countries", ignore = true)
    Language toEntity(LanguageDto dto);

    @Named("mappingRuleCountryId")
    default List<Long> mappingRuleCountryId(List<Country> countries) {
        if (countries == null || countries.isEmpty()) return List.of();
        return countries.stream()
                .map(Country::getId)   // lấy id
                .collect(Collectors.toList());
    }

    @Named("mappingRuleCountryName")
    default Set<String> mappingRuleCountryName(List<Country> countries) {
        if (countries == null || countries.isEmpty()) return Set.of();
        return countries.stream()
                .map(Country::getName)   // lấy name
                .collect(Collectors.toSet());
    }
}


