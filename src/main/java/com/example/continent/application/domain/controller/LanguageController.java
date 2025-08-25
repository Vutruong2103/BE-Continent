package com.example.continent.application.domain.controller;
import com.example.continent.application.domain.service.LanguageService;
import com.example.continent.application.dto.EthnicGroupDto;
import com.example.continent.application.dto.LanguageDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping
    public ResponseEntity<LanguageDto> create(@RequestBody LanguageDto dto) {
        return ResponseEntity.ok(languageService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LanguageDto> update(@PathVariable Long id, @RequestBody LanguageDto dto) {
        return ResponseEntity.ok(languageService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        languageService.delete(id); // xóa mềm
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(languageService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<LanguageDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(languageService.getAll(pageable));
    }

//    @GetMapping("/{countryId}/language")
//    public List<LanguageDto> getLanguages(@PathVariable Long countryId) {
//        return languageService.getLanguagesByCountry(countryId);
//    }
}

