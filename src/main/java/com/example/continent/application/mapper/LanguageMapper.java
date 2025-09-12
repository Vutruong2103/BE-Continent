package com.example.continent.application.mapper;

import com.example.continent.application.domain.model.Country;
import com.example.continent.application.dto.LanguageDto;
import com.example.continent.application.domain.model.Language;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LanguageMapper {

    @Mapping(target = "countryIds", source = "language", qualifiedByName = "mapCountryIds")
    @Mapping(target = "countryNames", source = "language", qualifiedByName = "mapCountryNames")
    LanguageDto toDto(Language language);

    @Named("mapCountryIds")
    default List<Long> getCountryIds(Language language) {
        if (language == null || language.getCountries() == null) return List.of();
        return language.getCountries().stream().map(Country::getId).toList();
    }

    @Named("mapCountryNames")
    default Set<String> getCountryNames(Language language) {
        if (language == null || language.getCountries() == null) return Set.of();
        return language.getCountries().stream().map(Country::getName).collect(Collectors.toSet());
    }

    @Mapping(target = "countries", ignore = true)
    Language toEntity(LanguageDto dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(LanguageDto dto, @MappingTarget Language language);
}


