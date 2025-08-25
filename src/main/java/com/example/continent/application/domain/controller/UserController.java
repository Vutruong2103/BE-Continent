package com.example.continent.application.domain.controller;

import com.example.continent.application.domain.service.UserService;
import com.example.continent.application.dto.ContinentDto;
import com.example.continent.application.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "Quản lý User trong hệ thống")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Tạo mới User", description = "API dùng để tạo mới User với username, password và danh sách role")
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @Operation(summary = "Cập nhật User", description = "API cập nhật thông tin của một User theo ID")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @Operation(summary = "Xóa mềm User", description = "API xóa mềm User (chỉ đánh dấu deleted = true)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lấy User theo ID", description = "API trả về thông tin chi tiết một User")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @Operation(summary = "Danh sách User (có phân trang)", description = "API trả về danh sách tất cả User chưa bị xóa")
    @GetMapping
    public ResponseEntity<Page<UserDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(userService.getAll(pageable));
    }

    @GetMapping("/search")
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserDto>> searchByName(@RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(userService.searchByName(keyword));
    }
}
