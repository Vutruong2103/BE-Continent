package com.example.continent.application.domain.repository;

import com.example.continent.application.domain.model.EthnicGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EthnicGroupRepository extends JpaRepository<EthnicGroup, Long> {
}

