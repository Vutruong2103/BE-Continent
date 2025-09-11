package com.example.continent.domain.controller;

import com.example.continent.application.dto_.CountryDto;
import com.example.continent.application.dto_.EthnicGroupDto;
import com.example.continent.application.dto_.LanguageDto;
import com.example.continent.domain.service.CountryService;
import com.example.continent.domain.service.EthnicGroupService;
import com.example.continent.domain.service.LanguageService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;
    private final LanguageService languageService;
    private final EthnicGroupService ethnicGroupService;

    @Operation(summary = "Tạo quốc gia mới",
            description = "Nhận CountryDto từ client và lưu vào hệ thống. Trả về thông tin quốc gia vừa tạo.")
    @PostMapping
    public ResponseEntity<CountryDto> create(@RequestBody CountryDto dto) {
        return ResponseEntity.ok(countryService.create(dto));
    }

    @Operation(summary = "Cập nhật quốc gia",
            description = "Cập nhật thông tin quốc gia dựa theo ID. Trả về CountryDto sau khi cập nhật.")
    @PutMapping("/{id}")
    public ResponseEntity<CountryDto> update(@PathVariable Long id, @RequestBody CountryDto dto) {
        return ResponseEntity.ok(countryService.update(id, dto));
    }

    @Operation(summary = "Xóa quốc gia",
            description = "Xóa mềm quốc gia dựa theo ID. Không xóa vĩnh viễn trong DB.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        countryService.delete(id); // xóa mềm
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lấy thông tin quốc gia theo ID",
            description = "Trả về CountryDto của quốc gia có ID tương ứng.")
    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(countryService.getById(id));
    }

    @Operation(summary = "Lấy danh sách quốc gia (có phân trang)",
            description = "Trả về trang dữ liệu CountryDto theo Pageable (page, size, sort).")
    @GetMapping
    public ResponseEntity<Page<CountryDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(countryService.getAll(pageable));
    }

    @Operation(summary = "Lấy danh sách ngôn ngữ của một quốc gia (có phân trang)",
            description = "Trả về danh sách LanguageDto thuộc quốc gia dựa trên countryId.")
    @GetMapping("/{id}/languages")
    public ResponseEntity<Page<LanguageDto>> getLanguages(
            @PathVariable Long id,
            Pageable pageable) {
        return ResponseEntity.ok(countryService.getLanguagesByCountry(id, pageable));
    }

    @Operation(summary = "Lấy danh sách quốc gia theo châu lục",
            description = "Trả về danh sách CountryDto thuộc về một châu lục dựa trên continentId.")
    @GetMapping("/by-continent/{continentId}")
    public ResponseEntity<List<CountryDto>> getCountriesByContinent(@PathVariable Long continentId) {
        return ResponseEntity.ok(countryService.getCountriesByContinent(continentId));
    }

    @Operation(summary = "Lấy tất cả ngôn ngữ của một quốc gia",
            description = "Trả về danh sách LanguageDto của quốc gia dựa trên countryId (không phân trang).")
    @GetMapping("/{countryId}/language")
    public List<LanguageDto> getLanguages(@PathVariable Long countryId) {
        return languageService.getLanguagesByCountry(countryId);
    }

    @Operation(summary = "Lấy tất cả dân tộc của một quốc gia",
            description = "Trả về danh sách EthnicGroupDto của quốc gia dựa trên countryId.")
    @GetMapping("/{countryId}/ethnic-group")
    public List<EthnicGroupDto> getEthnicGroups(@PathVariable Long countryId) {
        return ethnicGroupService.getEthnicGroupsByCountry(countryId);
    }
}


