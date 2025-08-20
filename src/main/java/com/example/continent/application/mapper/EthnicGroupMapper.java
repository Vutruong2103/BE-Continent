package com.example.continent.application.mapper;

import com.example.continent.application.dto.EthnicGroupDto;
import com.example.continent.application.domain.model.EthnicGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EthnicGroupMapper {
    //toDto: Lấy entity.country.id → gán vào dto.countryId
    EthnicGroupDto toDto(EthnicGroup ethnicGroup);

    EthnicGroup toEntity(EthnicGroupDto dto);
}