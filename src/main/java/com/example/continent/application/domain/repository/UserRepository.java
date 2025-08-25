package com.example.continent.application.domain.repository;

import com.example.continent.application.domain.model.Continent;
import com.example.continent.application.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndDeletedFalse(Long id);
    Optional<User> findByUsernameAndDeletedFalse(String username);
    Page<User> findAllByDeletedFalse(Pageable pageable);
    List<User> findByUsernameContainingIgnoreCase(String keyword);
}
