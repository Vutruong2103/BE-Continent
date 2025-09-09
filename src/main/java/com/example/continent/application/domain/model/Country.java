package com.example.continent.application.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @ManyToMany
    @JoinTable(name = "country_ethnic_group",
            joinColumns = @JoinColumn(name = "country_id"),
            inverseJoinColumns = @JoinColumn(name = "ethnic_group_id"))
    private List<EthnicGroup> ethnicGroups;

    @PrePersist @PreUpdate
    void upcase() { if (code != null) code = code.toUpperCase(); }
}
