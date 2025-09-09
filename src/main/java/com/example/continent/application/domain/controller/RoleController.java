package com.example.continent.application.domain.controller;

import com.example.continent.application.domain.service.ContinentService;
import com.example.continent.application.domain.service.RoleService;
import com.example.continent.application.dto.RoleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Role API", description = "Quản lý Role trong hệ thống")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "Tạo mới Role", description = "API dùng để tạo mới một Role")
    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody RoleDto dto) {
        return ResponseEntity.ok(roleService.create(dto));
    }

    @Operation(summary = "Cập nhật Role", description = "API cập nhật thông tin Role theo ID")
    @PutMapping("/{id}")
    public ResponseEntity<RoleDto> update(@PathVariable Long id, @RequestBody RoleDto dto) {
        return ResponseEntity.ok(roleService.update(id, dto));
    }

    @Operation(summary = "Xóa mềm Role", description = "API xóa mềm Role (chỉ đánh dấu deleted = true)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lấy Role theo ID", description = "API trả về chi tiết một Role")
    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

    @Operation(summary = "Danh sách Role (có phân trang)", description = "API trả về danh sách tất cả Role chưa bị xóa")
    @GetMapping
    public ResponseEntity<Page<RoleDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(roleService.getAll(pageable));
    }
}
