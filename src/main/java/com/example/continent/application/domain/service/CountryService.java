package com.example.continent.application.domain.service;

import com.example.continent.application.dto.CountryDto;
import com.example.continent.application.dto.LanguageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CountryService {
    CountryDto create(CountryDto dto);
    CountryDto update(Long id, CountryDto dto);
    void delete(Long id);
    CountryDto getById(Long id);
    Page<CountryDto> getAll(Pageable pageable);

    Page<LanguageDto> getLanguagesByCountry(Long countryId, Pageable pageable);



}

