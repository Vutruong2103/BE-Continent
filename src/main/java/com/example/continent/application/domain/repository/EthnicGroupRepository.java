package com.example.continent.application.domain.repository;

import com.example.continent.application.domain.model.EthnicGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface EthnicGroupRepository extends JpaRepository<EthnicGroup, Long> {
    Optional<EthnicGroup> findByCodeAndDeletedFalse(String code);
    Optional<EthnicGroup> findByIdAndDeletedFalse(Long id);
    Page<EthnicGroup> findAllByDeletedFalse(Pageable pageable);
}

