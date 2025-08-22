package com.example.continent.application.domain.controller;

import com.example.continent.application.domain.service.ContinentService;
import com.example.continent.application.dto.ContinentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/continents")
@RequiredArgsConstructor
public class ContinentController {

    private final ContinentService continentService;

    @PostMapping
    public ResponseEntity<ContinentDto> create(@RequestBody ContinentDto dto) {
        return ResponseEntity.ok(continentService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContinentDto> update(@PathVariable Long id, @RequestBody ContinentDto dto) {
        return ResponseEntity.ok(continentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        continentService.delete(id); // xóa mềm
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContinentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(continentService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ContinentDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(continentService.getAll(pageable)); // chỉ lấy deleted = false
    }
}

