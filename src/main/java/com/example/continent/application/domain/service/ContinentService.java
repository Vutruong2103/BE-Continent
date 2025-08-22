package com.example.continent.application.domain.service;

import com.example.continent.application.dto.ContinentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContinentService {
    ContinentDto create(ContinentDto dto);
    ContinentDto update(Long id, ContinentDto dto);
    void delete(Long id);
    ContinentDto getById(Long id);
    Page<ContinentDto> getAll(Pageable pageable);
}
