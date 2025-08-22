package com.example.continent.application.domain.controller;

import com.example.continent.application.domain.service.CountryService;
import com.example.continent.application.dto.CountryDto;
import com.example.continent.application.dto.LanguageDto;
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

    @PostMapping
    public ResponseEntity<CountryDto> create(@RequestBody CountryDto dto) {
        return ResponseEntity.ok(countryService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDto> update(@PathVariable Long id, @RequestBody CountryDto dto) {
        return ResponseEntity.ok(countryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        countryService.delete(id); // xóa mềm
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(countryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CountryDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(countryService.getAll(pageable));
    }

    @GetMapping("/{id}/languages")
    public ResponseEntity<Page<LanguageDto>> getLanguages(
            @PathVariable Long id,
            Pageable pageable) {
        return ResponseEntity.ok(countryService.getLanguagesByCountry(id, pageable));
    }

}


