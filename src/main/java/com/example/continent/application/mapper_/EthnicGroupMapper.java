package com.example.continent.application.mapper_;

import com.example.continent.application.dto_.EthnicGroupDto;
import com.example.continent.domain.model_.Country;
import com.example.continent.domain.model_.EthnicGroup;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;
/**
 * @TODO : update thông tin không có hã em chai ? 
 */
@Mapper(componentModel = "spring")
public interface EthnicGroupMapper {
    @Mapping(target = "countryId", source = "countries", qualifiedByName = "mappingRuleCountryId")

    EthnicGroupDto toDto(EthnicGroup ethnicGroup);

    @Mapping(target = "countries", ignore = true)
    EthnicGroup toEntity(EthnicGroupDto dto);

    @Named("mappingRuleCountryId")
    default List<Long> mappingRuleCountryId(List<Country> countries) {
        if (countries == null || countries.isEmpty()) return List.of();
        return countries.stream()
                .map(Country::getId)
                .collect(Collectors.toList());
    }


}