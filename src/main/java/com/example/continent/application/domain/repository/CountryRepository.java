package com.example.continent.application.domain.repository;

import com.example.continent.application.domain.model.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author :Vutq
 *
 * @findByCodeAndDeletedFalse: Tìm kiếm quốc gia theo mã và không bị xóa
 * @findByIdAndDeletedFalse: Tìm kiếm quốc gia theo ID và không bị xóa
 * @findAllByDeletedFalse: Tìm kiếm tất cả các quốc gia không bị xóa với phân trang
 * @findByContinentId: Tìm kiếm tất cả các quốc gia theo ID châu lục
 */
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCodeAndDeletedFalse(String code);

    Optional<Country> findByIdAndDeletedFalse(Long id);

    Page<Country> findAllByDeletedFalse(Pageable pageable);

    List<Country> findByContinentId(Long continentId);
}

