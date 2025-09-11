package com.example.continent.domain.controller;

import com.example.continent.application.dto_.ContinentDto;
import com.example.continent.domain.service.ContinentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * nơi request/response từ client
 * ResponseEntity<ContinentDto> = dữ liệu + status code + headers.
 * create:
 * @RequestBody ContinentDto dto: Nhận JSON từ request body và convert nó sang object ContinentDto.
 * Gọi xuống service để xử lý lưu dữ liệu (ví dụ insert DB).
 * Trả về ContinentDto sau khi đã lưu thành công.
 */

@RestController
@RequestMapping("/api/continents")
@RequiredArgsConstructor
public class ContinentController {

    private final ContinentService continentService;

    @Operation(summary = "Tạo châu lục mới",
            description = "Nhận ContinentDto từ client và lưu vào hệ thống. Trả về thông tin châu lục vừa tạo.")
    @PostMapping
    public ResponseEntity<ContinentDto> create(@RequestBody ContinentDto dto) {
        return ResponseEntity.ok(continentService.create(dto));
    }

    @Operation(summary = "Cập nhật châu lục",
            description = "Cập nhật thông tin châu lục dựa theo ID. Trả về ContinentDto sau khi cập nhật.")
    @PutMapping("/{id}")
    public ResponseEntity<ContinentDto> update(@PathVariable Long id, @RequestBody ContinentDto dto) {
        return ResponseEntity.ok(continentService.update(id, dto));
    }

    @Operation(summary = "Xóa châu lục",
            description = "Xóa mềm châu lục dựa theo ID. Không xóa vĩnh viễn trong DB.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        continentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lấy thông tin châu lục theo ID",
            description = "Trả về ContinentDto của châu lục có ID tương ứng.")
    @GetMapping("/{id}")
    public ResponseEntity<ContinentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(continentService.getById(id));
    }

    @Operation(summary = "Lấy danh sách châu lục (có phân trang)",
            description = "Trả về trang dữ liệu ContinentDto theo Pageable (page, size, sort).")
    @GetMapping
    public ResponseEntity<Page<ContinentDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(continentService.getAll(pageable));
    }

    @Operation(summary = "Tìm kiếm châu lục theo tên",
            description = "Tìm kiếm các châu lục có tên khớp với từ khóa (keyword). Trả về danh sách ContinentDto.")
    @GetMapping("/search")
    public ResponseEntity<List<ContinentDto>> searchByName(@RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(continentService.searchByName(keyword));
    }
}

