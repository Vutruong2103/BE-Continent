package com.example.continent.application.domain.repository;

import com.example.continent.application.domain.model.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ContinentRepository extends JpaRepository<Continent, Long> {
}
