package com.example.continent.application.domain.controller;

import com.example.continent.application.domain.service.CountryService;
import com.example.continent.application.dto.CountryDto;
import com.example.continent.application.dto.LanguageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/{id}/languages")
    public List<LanguageDto> getLanguagesByCountry(@PathVariable Long id) {
        return countryService.getLanguagesByCountry(id);
    }

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
        countryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(countryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAll() {
        return ResponseEntity.ok(countryService.getAll());
    }
}
