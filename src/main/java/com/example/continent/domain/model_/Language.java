    package com.example.continent.domain.model_;

    import jakarta.persistence.*;
    import lombok.*;

    import java.util.List;

    @Entity
    @Table(name = "language",
            uniqueConstraints = { @UniqueConstraint(columnNames = "code") })
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

    public class Language extends AbstractAuditingEntity<Long>{

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 100)
        private String name;

        @Column(nullable = false, length = 10, unique = true)
        private String code;

        @ManyToMany(mappedBy = "languages")
        private List<Country> countries;

        @PrePersist @PreUpdate
        void upcase() { if (code != null) code = code.toUpperCase();

        }
    }

