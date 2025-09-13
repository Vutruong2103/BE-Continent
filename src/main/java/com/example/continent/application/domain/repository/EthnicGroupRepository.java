package com.example.continent.application.domain.repository;

import com.example.continent.application.domain.model.EthnicGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author : Vutq
 *
 * Lấy đối tượng EthnicGroup (dân tộc).
 * Từ dân tộc (e) nối sang các quốc gia (countries) mà nó thuộc.
 * Từ quốc gia (c) nối tiếp sang châu lục (continent).
 * Lọc theo continentId được truyền vào. và Chỉ lấy những dân tộc chưa bị xoá mềm.
 *
 * @Query: Lấy dân tộc theo id châu lục
 */
public interface EthnicGroupRepository extends JpaRepository<EthnicGroup, Long> {

    Optional<EthnicGroup> findByCodeAndDeletedFalse(String code);

    @EntityGraph(attributePaths = "countries")
    Optional<EthnicGroup> findByIdAndDeletedFalse(Long id);

    @EntityGraph(attributePaths = "countries")
    Page<EthnicGroup> findAllByDeletedFalse(Pageable pageable);

    @EntityGraph(attributePaths = "countries")
    List<EthnicGroup> findByCountries_Id(Long countryId);

    @Query("SELECT DISTINCT e FROM EthnicGroup e " +
            "JOIN FETCH e.countries c " +
            "JOIN c.continent ct " +
            "WHERE ct.id = :continentId AND e.deleted = false")
    List<EthnicGroup> findByContinentId(@Param("continentId") Long continentId);
}

