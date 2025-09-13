package com.example.continent.application.mapper;


import com.example.continent.application.domain.model.Continent;
import com.example.continent.application.domain.model.EthnicGroup;
import com.example.continent.application.domain.model.Language;
import com.example.continent.application.dto.CountryDto;
import com.example.continent.application.domain.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author :Vutq
 *
 * @updateFromDto: cập nhật các trường của Country từ CountryDto, ngoại trừ id, languages và ethnicGroups
 * @toDto: chuyển đổi đối tượng Country sang CountryDto, ánh xạ các trường liên quan như continentId, continentName, languageIds, languageNames, ethnicGroupIds và ethnicGroupNames
 * @toEntity: chuyển đổi đối tượng CountryDto sang Country, ánh xạ trường continent từ continentId và bỏ qua languages và ethnicGroups khi chuyển đổi
 * @mapContinentIdToEntity: chuyển đổi continentId thành đối tượng Continent với chỉ id được thiết lập
 * @mapLanguageIds: lấy danh sách các id ngôn ngữ từ đối tượng Country
 * @mapLanguageNames: lấy tập hợp các tên ngôn ngữ từ đối tượng Country
 * @mapEthnicGroupIds: lấy danh sách các id nhóm dân tộc từ đối tượng Country
 * @mapEthnicGroupNames: lấy tập hợp các tên nhóm dân tộc từ đối tượng Country
 *
 * */
@Mapper(componentModel = "spring")
public interface CountryMapper {

    @Mapping(target = "continentId", source = "continent.id")
    @Mapping(target = "continentName", source = "continent.name")
    @Mapping(target = "languageIds", source = "country", qualifiedByName = "mapLanguageIds")
    @Mapping(target = "languageNames", source = "country", qualifiedByName = "mapLanguageNames")
    @Mapping(target = "ethnicGroupIds", source = "country", qualifiedByName = "mapEthnicGroupIds")
    @Mapping(target = "ethnicGroupNames", source = "country", qualifiedByName = "mapEthnicGroupNames")
    CountryDto toDto(Country country);

    @Mapping(target = "continent", source = "continentId", qualifiedByName = "mapContinentIdToEntity")
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "ethnicGroups", ignore = true)
    Country toEntity(CountryDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "continent", source = "continentId", qualifiedByName = "mapContinentIdToEntity")
    @Mapping(target = "languages", ignore = true)
    @Mapping(target = "ethnicGroups", ignore = true)
    void updateFromDto(CountryDto dto, @MappingTarget Country country);

    @Named("mapContinentIdToEntity")
    default Continent mapContinentIdToEntity(Long continentId) {
        if (continentId == null) return null;
        Continent continent = new Continent();
        continent.setId(continentId);
        return continent;
    }

    @Named("mapLanguageIds")
    default List<Long> getLanguageIds(Country country) {
        if (country == null || country.getLanguages() == null) return List.of();
        return country.getLanguages().stream().map(Language::getId).toList();
    }

    @Named("mapLanguageNames")
    default Set<String> getLanguageNames(Country country) {
        if (country == null || country.getLanguages() == null) return Set.of();
        return country.getLanguages().stream().map(Language::getName).collect(Collectors.toSet());
    }

    @Named("mapEthnicGroupIds")
    default List<Long> getEthnicGroupIds(Country country) {
        if (country == null || country.getEthnicGroups() == null) return List.of();
        return country.getEthnicGroups().stream().map(EthnicGroup::getId).toList();
    }

    @Named("mapEthnicGroupNames")
    default Set<String> getEthnicGroupNames(Country country) {
        if (country == null || country.getEthnicGroups() == null) return Set.of();
        return country.getEthnicGroups().stream().map(EthnicGroup::getName).collect(Collectors.toSet());
    }
}
