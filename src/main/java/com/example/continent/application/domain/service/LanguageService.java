package com.example.continent.application.domain.service;


import com.example.continent.application.dto.LanguageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LanguageService {
    LanguageDto create(LanguageDto dto);
    LanguageDto update(Long id, LanguageDto dto);
    void delete(Long id);
    LanguageDto getById(Long id);
    Page<LanguageDto> getAll(Pageable pageable);
    List<LanguageDto> getLanguagesByCountry(Long countryId);
}

