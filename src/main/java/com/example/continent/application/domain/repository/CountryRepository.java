package com.example.continent.application.domain.repository;

import com.example.continent.application.domain.model.Country;
import com.example.continent.application.domain.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("SELECT c.languages FROM Country c WHERE c.id = :id")
    List<Language> findLanguagesByCountryId(@Param("id") Long id);

}

