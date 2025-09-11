package com.example.continent.domain.repository_;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // dư 
import org.springframework.data.repository.query.Param; // dư 

import com.example.continent.domain.model_.Country;
import com.example.continent.domain.model_.Language; // dư

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCodeAndDeletedFalse(String code);
    Optional<Country> findByIdAndDeletedFalse(Long id);
    Page<Country> findAllByDeletedFalse(Pageable pageable);
    List<Country> findByContinentId(Long continentId);
}

