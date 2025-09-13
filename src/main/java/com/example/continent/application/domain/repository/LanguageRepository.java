package com.example.continent.application.domain.repository;

import com.example.continent.application.domain.model.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author : Vutq
 *
 * @findByCodeAndDeletedFalse: tìm kiếm ngôn ngữ theo mã (code) và chưa bị xóa mềm (deleted = false)
 * @findByIdAndDeletedFalse: tìm kiếm ngôn ngữ theo ID và chưa bị xóa mềm
 * @findAllByDeletedFalse: lấy tất cả ngôn ngữ chưa bị xóa mềm với phân trang
 * @findByCountries_Id: tìm kiếm ngôn ngữ theo ID quốc gia liên kết
 * @EntityGraph: Tối ưu hóa truy vấn để lấy dữ liệu liên quan (countries) cùng với ngôn ngữ, giảm số lượng truy vấn đến cơ sở dữ liệu.
 */
public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByCodeAndDeletedFalse(String code);

    Optional<Language> findByIdAndDeletedFalse(Long id);

    Page<Language> findAllByDeletedFalse(Pageable pageable);

    @EntityGraph(attributePaths = "countries")
    List<Language> findByCountries_Id(Long countryId);
}