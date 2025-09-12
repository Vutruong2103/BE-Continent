package com.example.continent.application.mapper;

import com.example.continent.application.domain.model.Country;
import com.example.continent.application.domain.model.EthnicGroup;
import com.example.continent.application.dto.ContinentDto;
import com.example.continent.application.domain.model.Continent;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ContinentMapper {

    @Mapping(target = "countryIds", source = "continent", qualifiedByName = "mapCountryIds")
    @Mapping(target = "countryNames", source = "continent", qualifiedByName = "mapCountryNames")
    @Mapping(target = "ethnicGroupIds", source = "continent", qualifiedByName = "mapEthnicGroupIds")
    @Mapping(target = "ethnicGroupNames", source = "continent", qualifiedByName = "mapEthnicGroupNames")
    ContinentDto toDto(Continent continent);

    @Named("mapCountryIds")
    default List<Long> getCountryIds(Continent continent) {
        if (continent == null || continent.getCountries() == null) return List.of();
        return continent.getCountries().stream().map(Country::getId).toList();
    }

    @Named("mapCountryNames")
    default Set<String> getCountryNames(Continent continent) {
        if (continent == null || continent.getCountries() == null) return Set.of();
        return continent.getCountries().stream().map(Country::getName).collect(Collectors.toSet());
    }

    @Named("mapEthnicGroupIds")
    default List<Long> getEthnicGroupIds(Continent continent) {
        if (continent == null || continent.getCountries() == null) return List.of();
        return continent.getCountries().stream()
                .flatMap(c -> c.getEthnicGroups().stream())
                .map(EthnicGroup::getId)
                .distinct().toList();
    }

    @Named("mapEthnicGroupNames")
    default Set<String> getEthnicGroupNames(Continent continent) {
        if (continent == null || continent.getCountries() == null) return Set.of();
        return continent.getCountries().stream()
                .flatMap(c -> c.getEthnicGroups().stream())
                .map(EthnicGroup::getName)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "countries", ignore = true) // chỉ set id
    Continent toEntity(ContinentDto dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(ContinentDto dto, @MappingTarget Continent continent);
}
