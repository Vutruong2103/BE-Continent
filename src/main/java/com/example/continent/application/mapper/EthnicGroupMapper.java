package com.example.continent.application.mapper;

import com.example.continent.application.domain.model.Country;
import com.example.continent.application.dto.EthnicGroupDto;
import com.example.continent.application.domain.model.EthnicGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EthnicGroupMapper {
//    @Mapping(target = "countryId", source = "countries", qualifiedByName = "mappingRuleCountryId")
//    @Mapping(target = "countryName", source = "countries", qualifiedByName = "mappingRuleCountryName")
    @Mapping(target = "countryId", source = "countries", qualifiedByName = "mappingRuleCountryId")
    //toDto: Lấy entity.country.id → gán vào dto.countryId
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

//    @Named("mappingRuleCountryId")
//    default List<Long> mappingRuleCountryId(List<Country> countries) {
//        if (countries == null || countries.isEmpty()) return List.of();
//        return countries.stream()
//                .map(Country::getId)   // lấy id
//                .collect(Collectors.toList());
//    }
//
//    @Named("mappingRuleCountryName")
//    default Set<String> mappingRuleCountryName(List<Country> countries) {
//        if (countries == null || countries.isEmpty()) return Set.of();
//        return countries.stream()
//                .map(Country::getName)   // lấy name
//                .collect(Collectors.toSet());
//    }

}