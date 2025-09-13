package com.example.continent.application.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author: Vutq
 *
 * @id: Khóa chính, tự động tăng.
 * @name: Tên châu lục, không được để trống, độ dài
 * @code: Mã châu lục, không được để trống, duy nhất, độ dài tối đa 10 ký tự.
 * @countries: Danh sách các quốc gia thuộc châu lục này.
 * @PrePersist @PreUpdate: Các phương thức này được gọi tự động trước khi thực hiện lưu mới hoặc cập nhật bản ghi.
 * @upcase: Chuyển đổi mã thành chữ hoa trước khi lưu hoặc cập nhật.
 */
@Entity
@Table(name = "continent",
        uniqueConstraints = { @UniqueConstraint(columnNames = "code") })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class Continent extends AbstractAuditingEntity<Long>{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 10, unique = true)
    private String code;

    @OneToMany(mappedBy = "continent", cascade = CascadeType.ALL)
    private List<Country> countries;

    @PrePersist @PreUpdate
    void upcase() { if (code != null) code = code.toUpperCase(); }
}
