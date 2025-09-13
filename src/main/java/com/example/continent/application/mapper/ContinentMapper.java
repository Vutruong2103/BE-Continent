package com.example.continent.application.mapper;

import com.example.continent.application.domain.model.Country;
import com.example.continent.application.domain.model.EthnicGroup;
import com.example.continent.application.dto.ContinentDto;
import com.example.continent.application.domain.model.Continent;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * author: Vutq
 *
 * @Mapping: ánh xạ các trường giữa Continent và ContinentDto.
 * @Named: định nghĩa các phương thức tùy chỉnh để ánh xạ các trường phức tạp như danh sách ID và tên.
 * @MappingTarget: cho phép cập nhật một thực thể hiện có từ một DTO.
 *
 * @getCountryIds: Lấy danh sách ID các quốc gia thuộc lục địa.
 * @getCountryNames: Lấy tập hợp tên các quốc gia thuộc lục địa.
 * @getEthnicGroupIds: Lấy danh sách ID các dân tộc thuộc các quốc gia trong lục địa.
 * @getEthnicGroupNames: Lấy tập hợp tên các dân tộc thuộc các quốc gia trong lục địa.
 * @toEntity: Chuyển đổi từ ContinentDto sang Continent, bỏ qua việc thiết lập danh sách quốc gia.
 * @updateFromDto: Cập nhật một thực thể Continent hiện có từ ContinentDto, bỏ qua việc cập nhật ID.
 */
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

    @Mapping(target = "countries", ignore = true)
    Continent toEntity(ContinentDto dto);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(ContinentDto dto, @MappingTarget Continent continent);
}
