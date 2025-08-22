package com.example.continent.application.domain.repository;

import com.example.continent.application.domain.model.Continent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ContinentRepository extends JpaRepository<Continent, Long> {
    Boolean existsByCode(String code);
    Optional<Continent> findByIdAndDeletedFalse(Long id);
    Page<Continent> findAllByDeletedFalse(Pageable pageable);
}
