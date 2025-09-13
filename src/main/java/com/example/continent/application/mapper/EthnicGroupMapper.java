package com.example.continent.application.mapper;

import com.example.continent.application.domain.model.Country;
import com.example.continent.application.dto.EthnicGroupDto;
import com.example.continent.application.domain.model.EthnicGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author : Vutq
 *
 * java(mapContinentId(entity.getCountries())): Biểu thức này gọi phương thức tùy chỉnh mapContinentId để trích xuất ID lục địa từ danh sách các quốc gia liên kết với nhóm dân tộc.
 * java(mapContinentName(entity.getCountries())): Tương tự, biểu thức này gọi phương thức tùy chỉnh mapContinentName để trích xuất tên lục địa từ danh sách các quốc gia liên kết với nhóm dân tộc.
 *
 */
@Mapper(componentModel = "spring")
public interface EthnicGroupMapper {

    @Mapping(target = "countryIds", source = "countries")
    @Mapping(target = "countryNames", source = "countries")
    @Mapping(target = "continentIds", expression = "java(mapContinentId(entity.getCountries()))")
    @Mapping(target = "continentNames", expression = "java(mapContinentName(entity.getCountries()))")
    EthnicGroupDto toDto(EthnicGroup entity);

    default List<Long> mapCountryIds(List<Country> countries) {
        if (countries == null) return List.of();
        return countries.stream().map(Country::getId).toList();
    }

    default Set<String> mapCountryNames(List<Country> countries) {
        if (countries == null) return Set.of();
        return countries.stream().map(Country::getName).collect(Collectors.toSet());
    }

    default Long mapContinentId(List<Country> countries) {
        if (countries == null || countries.isEmpty()) return null;
        return countries.get(0).getContinent().getId();
    }

    default String mapContinentName(List<Country> countries) {
        if (countries == null || countries.isEmpty()) return null;
        return countries.get(0).getContinent().getName();
    }

    @Mapping(target = "countries", ignore = true)
    EthnicGroup toEntity(EthnicGroupDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "countries", ignore = true)
    void updateFromDto(EthnicGroupDto dto, @MappingTarget EthnicGroup entity);
}




