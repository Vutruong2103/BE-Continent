package com.example.continent.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.continent.application.dto_.CountryDto;
import com.example.continent.application.dto_.LanguageDto;

import java.util.List;

public interface CountryService {
    CountryDto create(CountryDto dto);
    CountryDto update(Long id, CountryDto dto);
    void delete(Long id);
    CountryDto getById(Long id);
    Page<CountryDto> getAll(Pageable pageable);
    Page<LanguageDto> getLanguagesByCountry(Long countryId, Pageable pageable);
    List<CountryDto> getCountriesByContinent(Long continentId);
}

