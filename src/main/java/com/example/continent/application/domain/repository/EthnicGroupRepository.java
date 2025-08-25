package com.example.continent.application.domain.repository;

import com.example.continent.application.domain.model.Country;
import com.example.continent.application.domain.model.EthnicGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface EthnicGroupRepository extends JpaRepository<EthnicGroup, Long> {
    Optional<EthnicGroup> findByCodeAndDeletedFalse(String code);
    Optional<EthnicGroup> findByIdAndDeletedFalse(Long id);
    Page<EthnicGroup> findAllByDeletedFalse(Pageable pageable);


    @EntityGraph(attributePaths = "countries")
    List<EthnicGroup> findByCountries_Id(Long countryId);


    /*
    * Lấy đối tượng EthnicGroup (dân tộc).
    * Từ dân tộc (e) nối sang các quốc gia (countries) mà nó thuộc.
    * Từ quốc gia (c) nối tiếp sang châu lục (continent).
    * Lọc theo continentId được truyền vào. và Chỉ lấy những dân tộc chưa bị xoá mềm.
    * */
    // Lấy dân tộc theo id châu lục
    @Query("SELECT e FROM EthnicGroup e " +
            "JOIN e.countries c " +
            "JOIN c.continent ct " +
            "WHERE ct.id = :continentId AND e.deleted = false")
    List<EthnicGroup> findByContinentId(@Param("continentId") Long continentId);
}

