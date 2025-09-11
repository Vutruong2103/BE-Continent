package com.example.continent.domain.repository_;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.continent.domain.model_.Role;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameAndDeletedFalse(String name);
    Optional<Role> findByIdAndDeletedFalse(Long id);
    Page<Role> findAllByDeletedFalse(Pageable pageable);
}