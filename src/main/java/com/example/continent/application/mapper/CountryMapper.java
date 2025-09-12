package com.example.continent.application.mapper;


import com.example.continent.application.domain.model.Continent;
import com.example.continent.application.domain.model.EthnicGroup;
import com.example.continent.application.domain.model.Language;
import com.example.continent.application.dto.CountryDto;
import com.example.continent.application.domain.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    @Mapping(target = "continentId", source = "continent.id")
    @Mapping(target = "continentName", source = "continent.name")
    @Mapping(target = "languageIds", source = "country", qualifiedByName = "mapLanguageIds")
    @Mapping(target = "languageNames", source = "country", qualifiedByName = "mapLanguageNames")
    @Mapping(target = "ethnicGroupIds", source = "country", qualifiedByName = "mapEthnicGroupIds")
    @Mapping(target = "ethnicGroupNames", source = "country", qualifiedByName = "mapEthnicGroupNames")
    CountryDto toDto(Country country);

    @Mapping(target = "continent", source = "continentId", qualifiedByName = "mapContinentIdToEntity")
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "ethnicGroups", ignore = true)
    Country toEntity(CountryDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "continent", source = "continentId", qualifiedByName = "mapContinentIdToEntity")
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "ethnicGroups", ignore = true)
    void updateFromDto(CountryDto dto, @MappingTarget Country country);

    @Named("mapContinentIdToEntity")
    default Continent mapContinentIdToEntity(Long continentId) {
        if (continentId == null) return null;
        Continent continent = new Continent();
        continent.setId(continentId);
        return continent;
    }

    @Named("mapLanguageIds")
    default List<Long> getLanguageIds(Country country) {
        if (country == null || country.getLanguages() == null) return List.of();
        return country.getLanguages().stream().map(Language::getId).toList();
    }

    @Named("mapLanguageNames")
    default Set<String> getLanguageNames(Country country) {
        if (country == null || country.getLanguages() == null) return Set.of();
        return country.getLanguages().stream().map(Language::getName).collect(Collectors.toSet());
    }

    @Named("mapEthnicGroupIds")
    default List<Long> getEthnicGroupIds(Country country) {
        if (country == null || country.getEthnicGroups() == null) return List.of();
        return country.getEthnicGroups().stream().map(EthnicGroup::getId).toList();
    }

    @Named("mapEthnicGroupNames")
    default Set<String> getEthnicGroupNames(Country country) {
        if (country == null || country.getEthnicGroups() == null) return Set.of();
        return country.getEthnicGroups().stream().map(EthnicGroup::getName).collect(Collectors.toSet());
    }
}
