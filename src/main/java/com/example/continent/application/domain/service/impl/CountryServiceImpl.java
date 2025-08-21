package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.Continent;
import com.example.continent.application.domain.model.Country;
import com.example.continent.application.domain.repository.ContinentRepository;
import com.example.continent.application.domain.repository.CountryRepository;
import com.example.continent.application.domain.service.CountryService;
import com.example.continent.application.dto.CountryDto;
import com.example.continent.application.dto.LanguageDto;
import com.example.continent.application.exception.ResourceNotFoundException;
import com.example.continent.application.mapper.CountryMapper;

import com.example.continent.application.mapper.LanguageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ContinentRepository continentRepository;
    private final CountryMapper countryMapper;
    private final LanguageMapper languageMapper;

    @Override
    @Transactional(readOnly = true)
    public List<LanguageDto> getLanguagesByCountry(Long countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country with id " + countryId + " not found"));

        return country.getLanguages()
                .stream()
                .map(languageMapper::toDto)
                .toList();
    }

    @Override
    public CountryDto create(CountryDto dto) {
        Continent continent = continentRepository.findById(dto.getContinentId())
                .orElseThrow(() -> new ResourceNotFoundException("Continent with id " + dto.getContinentId() + " not found"));

        Country country = countryMapper.toEntity(dto);
        country.setContinent(continent);
        return countryMapper.toDto(countryRepository.save(country));
    }

    @Override
    public CountryDto update(Long id, CountryDto dto) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country with id " + id + " not found"));

        Continent continent = continentRepository.findById(dto.getContinentId())
                .orElseThrow(() -> new ResourceNotFoundException("Continent with id " + dto.getContinentId() + " not found"));

        country.setCode(dto.getCode());
        country.setName(dto.getName());
        country.setContinent(continent);

        return countryMapper.toDto(countryRepository.save(country));
    }

    @Override
    public void delete(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Country with id " + id + " not found");
        }
        countryRepository.deleteById(id);
    }

    @Override
    public CountryDto getById(Long id) {
        return countryRepository.findById(id)
                .map(countryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Country with id " + id + " not found"));
    }

    @Override
    public List<CountryDto> getAll() {
        return countryRepository.findAll()
                .stream()
                .map(countryMapper::toDto)
                .collect(Collectors.toList());
    }
}
