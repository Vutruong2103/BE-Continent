package com.example.continent.domain.controller_;
import com.example.continent.application.dto_.LanguageDto;
import com.example.continent.domain.service.LanguageService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @Operation(summary = "Tạo ngôn ngữ mới",
            description = "Nhận LanguageDto từ client và lưu vào hệ thống. Trả về thông tin ngôn ngữ vừa tạo.")
    @PostMapping
    public ResponseEntity<LanguageDto> create(@RequestBody LanguageDto dto) {
        return ResponseEntity.ok(languageService.create(dto));
    }

    @Operation(summary = "Cập nhật ngôn ngữ",
            description = "Cập nhật thông tin ngôn ngữ dựa theo ID. Trả về LanguageDto sau khi cập nhật.")
    @PutMapping("/{id}")
    public ResponseEntity<LanguageDto> update(@PathVariable Long id, @RequestBody LanguageDto dto) {
        return ResponseEntity.ok(languageService.update(id, dto));
    }

    @Operation(summary = "Xóa ngôn ngữ",
            description = "Xóa mềm ngôn ngữ dựa theo ID. Không xóa vĩnh viễn trong DB.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        languageService.delete(id); // xóa mềm
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lấy thông tin ngôn ngữ theo ID",
            description = "Trả về LanguageDto của ngôn ngữ có ID tương ứng.")
    @GetMapping("/{id}")
    public ResponseEntity<LanguageDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(languageService.getById(id));
    }

    @Operation(summary = "Lấy danh sách ngôn ngữ (có phân trang)",
            description = "Trả về trang dữ liệu LanguageDto theo Pageable (page, size, sort).")
    @GetMapping
    public ResponseEntity<Page<LanguageDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(languageService.getAll(pageable));
    }

}

