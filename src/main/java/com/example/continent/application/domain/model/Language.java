    package com.example.continent.application.domain.model;

    import jakarta.persistence.*;
    import lombok.*;
    import org.hibernate.annotations.SQLDelete;
    import org.hibernate.annotations.Where;

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

//        @Column(name = "deleted", nullable = false)
//        private Boolean deleted ;

        @PrePersist @PreUpdate
        void upcase() { if (code != null) code = code.toUpperCase();

        }
    }

