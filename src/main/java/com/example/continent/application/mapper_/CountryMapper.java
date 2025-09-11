package com.example.continent.application.mapper_;


import com.example.continent.application.dto_.CountryDto;
import com.example.continent.domain.model_.Country;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    @Mapping(source = "continent.id", target = "continentId")
    CountryDto toDto(Country country);

    @Mapping(source = "continentId", target = "continent.id")
    Country toEntity(CountryDto dto); // <- cái ni còn ethnicGroups, languages nữa không lấy ra à em  ?
}
