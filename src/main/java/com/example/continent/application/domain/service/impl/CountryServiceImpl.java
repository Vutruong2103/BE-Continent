package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.Continent;
import com.example.continent.application.domain.model.Country;
import com.example.continent.application.domain.repository.ContinentRepository;
import com.example.continent.application.domain.repository.CountryRepository;
import com.example.continent.application.domain.service.CountryService;
import com.example.continent.application.dto.CountryDto;
import com.example.continent.application.dto.LanguageDto;
import com.example.continent.application.exception.DuplicateResourceException;
import com.example.continent.application.exception.ResourceNotFoundException;
import com.example.continent.application.mapper.CountryMapper;
import com.example.continent.application.mapper.LanguageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ContinentRepository continentRepository;
    private final CountryMapper countryMapper;
    private final LanguageMapper languageMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public Page<LanguageDto> getLanguagesByCountry(Long countryId, Pageable pageable) {
        Country country = countryRepository.findByIdAndDeletedFalse(countryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.country.notfound",
                                new Object[]{countryId}, LocaleContextHolder.getLocale())
                ));

        return country.getLanguages()
                .stream()
                .filter(lang -> !Boolean.TRUE.equals(lang.getDeleted())) // chỉ lấy language chưa xóa
                .map(languageMapper::toDto)
                .collect(java.util.stream.Collectors.collectingAndThen(
                        java.util.stream.Collectors.toList(),
                        list -> new org.springframework.data.domain.PageImpl<>(list, pageable, list.size())
                ));
    }

    @Override
    public CountryDto create(CountryDto dto) {
        // Check duplicate code
        countryRepository.findByCodeAndDeletedFalse(dto.getCode())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.country.exists",
                                    new Object[]{dto.getCode()}, LocaleContextHolder.getLocale())
                    );
                });

        Continent continent = continentRepository.findByIdAndDeletedFalse(dto.getContinentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.continent.notfound",
                                new Object[]{dto.getContinentId()}, LocaleContextHolder.getLocale())
                ));

        Country country = countryMapper.toEntity(dto);
        country.setContinent(continent);
        country.setDeleted(false);

        return countryMapper.toDto(countryRepository.save(country));
    }

    @Override
    public CountryDto update(Long id, CountryDto dto) {
        Country country = countryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.country.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));

        // Check duplicate code (exclude itself)
        countryRepository.findByCodeAndDeletedFalse(dto.getCode())
                .filter(c -> !c.getId().equals(id))
                .ifPresent(c -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.country.exists",
                                    new Object[]{dto.getCode()}, LocaleContextHolder.getLocale())
                    );
                });

        Continent continent = continentRepository.findByIdAndDeletedFalse(dto.getContinentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.continent.notfound",
                                new Object[]{dto.getContinentId()}, LocaleContextHolder.getLocale())
                ));

        country.setCode(dto.getCode());
        country.setName(dto.getName());
        country.setContinent(continent);

        return countryMapper.toDto(countryRepository.save(country));
    }

    @Override
    public void delete(Long id) {
        Country country = countryRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.country.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
        country.setDeleted(true);
        countryRepository.save(country);
    }

    @Override
    @Transactional(readOnly = true)
    public CountryDto getById(Long id) {
        return countryRepository.findByIdAndDeletedFalse(id)
                .map(countryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.country.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CountryDto> getAll(Pageable pageable) {
        return countryRepository.findAllByDeletedFalse(pageable)
                .map(countryMapper::toDto);
    }

    @Override
    public List<CountryDto> getCountriesByContinent(Long continentId) {
        List<Country> countries = countryRepository.findByContinentId(continentId);
        return countries.stream()
                .map(countryMapper::toDto)
                .toList();
    }


}
