package com.example.continent.application.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author : Vutq
 *
 * @continent: Biểu thị một thực thể quốc gia với các thuộc tính như tên, mã, lục địa, ngôn ngữ và nhóm dân tộc.
 * @languages: Danh sách các ngôn ngữ được sử dụng trong quốc gia này.
 * @ethnicGroups: Danh sách các nhóm dân tộc sinh sống trong quốc gia này
 * @joinColumns: Chỉ định cột khóa ngoại trong bảng liên kết trỏ đến thực thể hiện tại (Country).
 * @inverseJoinColumns: Chỉ định cột khóa ngoại trong bảng liên kết trỏ đến thực thể liên kết (Language).
 */
@Entity
@Table(name = "country",
        uniqueConstraints = { @UniqueConstraint(columnNames = "code") })
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class Country extends AbstractAuditingEntity<Long>{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 10, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "continent_id", nullable = false)
    private Continent continent;

    @ManyToMany
    @JoinTable(name = "country_language",
            joinColumns = @JoinColumn(name = "country_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id"))
    private List<Language> languages;

    @ManyToMany(mappedBy = "countries")
    private List<EthnicGroup> ethnicGroups;

    @PrePersist @PreUpdate
    void upcase() { if (code != null) code = code.toUpperCase(); }
}
