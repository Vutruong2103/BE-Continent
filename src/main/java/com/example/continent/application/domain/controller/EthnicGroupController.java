package com.example.continent.application.domain.controller;

import com.example.continent.application.domain.service.EthnicGroupService;
import com.example.continent.application.dto.EthnicGroupDto;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Tạo dân tộc mới",
            description = "Nhận EthnicGroupDto từ client và lưu vào hệ thống. Trả về thông tin dân tộc vừa tạo.")
    @PostMapping
    public ResponseEntity<EthnicGroupDto> create(@RequestBody EthnicGroupDto dto) {
        return ResponseEntity.ok(ethnicGroupService.create(dto));
    }

    @Operation(summary = "Cập nhật dân tộc",
            description = "Cập nhật thông tin dân tộc dựa theo ID. Trả về EthnicGroupDto sau khi cập nhật.")
    @PutMapping("/{id}")
    public ResponseEntity<EthnicGroupDto> update(@PathVariable Long id, @RequestBody EthnicGroupDto dto) {
        return ResponseEntity.ok(ethnicGroupService.update(id, dto));
    }

    @Operation(summary = "Xóa dân tộc",
            description = "Xóa mềm dân tộc dựa theo ID. Không xóa vĩnh viễn trong DB.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ethnicGroupService.delete(id); // xóa mềm
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lấy thông tin dân tộc theo ID",
            description = "Trả về EthnicGroupDto của dân tộc có ID tương ứng.")
    @GetMapping("/{id}")
    public ResponseEntity<EthnicGroupDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ethnicGroupService.getById(id));
    }

    @Operation(summary = "Lấy danh sách dân tộc (có phân trang)",
            description = "Trả về trang dữ liệu EthnicGroupDto theo Pageable (page, size, sort).")
    @GetMapping
    public ResponseEntity<Page<EthnicGroupDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(ethnicGroupService.getAll(pageable));
    }

    @Operation(summary = "Lấy danh sách dân tộc theo châu lục",
            description = "Trả về danh sách EthnicGroupDto thuộc về một châu lục dựa trên continentId.")
    @GetMapping("/by-continent/{continentId}")
    public ResponseEntity<List<EthnicGroupDto>> getByContinent(@PathVariable Long continentId) {
        return ResponseEntity.ok(ethnicGroupService.getByContinent(continentId));
    }

}
