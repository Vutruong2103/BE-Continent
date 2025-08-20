package com.example.continent.application.domain.repository;

import com.example.continent.application.domain.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByCodeAndDeletedFalse(String code);
}