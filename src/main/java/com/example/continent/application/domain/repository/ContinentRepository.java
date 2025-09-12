package com.example.continent.application.domain.repository;

import com.example.continent.application.domain.model.Continent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository là lớp trung gian giữa Service và Database
 * JpaRepository: kh cần viết sql, tự sinh CRUD, save(),findById(),findAll(),deleteById()
 * Continent: Entity mà repository sẽ thao tác
 * Long: kiểu dl của primary key (id)
 * Pageable chứa thông tin phân trang (số trang, số phần tử/trang, sort)
 */

public interface ContinentRepository extends JpaRepository<Continent, Long> {

    Optional<Continent> findByCodeAndDeletedFalse(String code);

    Optional<Continent> findByIdAndDeletedFalse(Long id);

    Page<Continent> findAllByDeletedFalse(Pageable pageable);

    Page<Continent> findByNameContainingIgnoreCaseAndDeletedFalse(String keyword, Pageable pageable);
}
