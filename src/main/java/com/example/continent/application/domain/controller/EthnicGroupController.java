package com.example.continent.application.domain.controller;

import com.example.continent.application.domain.service.EthnicGroupService;
import com.example.continent.application.dto.EthnicGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ethnic-groups")
@RequiredArgsConstructor
public class EthnicGroupController {

    private final EthnicGroupService ethnicGroupService;

    @PostMapping
    public ResponseEntity<EthnicGroupDto> create(@RequestBody EthnicGroupDto dto) {
        return ResponseEntity.ok(ethnicGroupService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EthnicGroupDto> update(@PathVariable Long id, @RequestBody EthnicGroupDto dto) {
        return ResponseEntity.ok(ethnicGroupService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ethnicGroupService.delete(id); // xóa mềm
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EthnicGroupDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ethnicGroupService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<EthnicGroupDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(ethnicGroupService.getAll(pageable));
    }
}
