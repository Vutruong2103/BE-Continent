package com.example.continent.application.domain.repository;

import com.example.continent.application.domain.model.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByCodeAndDeletedFalse(String code);

    Optional<Language> findByIdAndDeletedFalse(Long id);

    Page<Language> findAllByDeletedFalse(Pageable pageable);

    @EntityGraph(attributePaths = "countries")
    List<Language> findByCountries_Id(Long countryId);
}