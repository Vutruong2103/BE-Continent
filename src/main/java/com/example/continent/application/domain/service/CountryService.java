package com.example.continent.application.domain.service;

import com.example.continent.application.dto.CountryDto;
import com.example.continent.application.dto.LanguageDto;

import java.util.List;

public interface CountryService {
    CountryDto create(CountryDto dto);
    CountryDto update(Long id, CountryDto dto);
    void delete(Long id);
    CountryDto getById(Long id);
    List<CountryDto> getAll();

    List<LanguageDto> getLanguagesByCountry(Long countryId);

}

