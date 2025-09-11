package com.example.continent.application.mapper;

import com.example.continent.application.dto.ContinentDto;
import com.example.continent.application.domain.model.Continent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContinentMapper {
    ContinentDto toDto(Continent continent);

    Continent toEntity(ContinentDto dto);
}