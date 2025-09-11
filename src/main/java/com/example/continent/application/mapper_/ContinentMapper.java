package com.example.continent.application.mapper_;

import com.example.continent.application.dto_.ContinentDto;
import com.example.continent.domain.model_.Continent;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContinentMapper {
    ContinentDto toDto(Continent continent);

    Continent toEntity(ContinentDto dto); // <- Anh nghi ngờ cái ni có thể sai 
}