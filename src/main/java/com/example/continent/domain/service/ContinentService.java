package com.example.continent.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.continent.application.dto_.ContinentDto;

import java.util.List;

/**
 * nơi chứa các method cho controller sử dụng
 */

public interface ContinentService {
    ContinentDto create(ContinentDto dto);
    ContinentDto update(Long id, ContinentDto dto);
    void delete(Long id);
    ContinentDto getById(Long id);
    Page<ContinentDto> getAll(Pageable pageable);
    List<ContinentDto> searchByName(String keyword);
}
