package com.example.continent.domain.repository_;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;//dư

import com.example.continent.domain.model_.Continent;

import java.util.List;
import java.util.Optional;

/**
* Repository là lớp trung gian giữa Service và Database
* JpaRepository: kh cần viết sql, tự sinh CRUD, save(),findById(),findAll(),deleteById()
* Continent: Entity mà repository sẽ thao tác
* Long: kiểu dl của primary key (id)
* Pageable chứa thông tin phân trang (số trang, số phần tử/trang, sort)
*/

public interface ContinentRepository extends JpaRepository<Continent, Long> {
    Boolean existsByCode(String code);
    Optional<Continent> findByIdAndDeletedFalse(Long id);
    Page<Continent> findAllByDeletedFalse(Pageable pageable);
    List<Continent> findByNameContainingIgnoreCase(String keyword); // rồi lỡ từ nớ họ nhập vô dưới DB là đánh dấu xóa rồi răng ?
}
