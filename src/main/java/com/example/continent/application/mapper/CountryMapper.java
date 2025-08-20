package com.example.continent.application.mapper;


import com.example.continent.application.dto.CountryDto;
import com.example.continent.application.domain.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    @Mapping(source = "continent.id", target = "continentId")
    CountryDto toDto(Country country);

    @Mapping(source = "continentId", target = "continent.id")
    Country toEntity(CountryDto dto);
}
