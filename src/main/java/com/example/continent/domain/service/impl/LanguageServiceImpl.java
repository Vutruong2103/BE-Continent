package com.example.continent.domain.service.impl;

import com.example.continent.application.dto_.LanguageDto;
import com.example.continent.application.exception_.DuplicateResourceException;
import com.example.continent.application.exception_.ResourceNotFoundException;
import com.example.continent.application.mapper_.LanguageMapper;
import com.example.continent.domain.model_.Country;
import com.example.continent.domain.model_.Language;
import com.example.continent.domain.repository_.CountryRepository;
import com.example.continent.domain.repository_.LanguageRepository;
import com.example.continent.domain.service.LanguageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;
    private final CountryRepository countryRepository;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public LanguageDto create(LanguageDto dto) {
        log.debug(">>> LanguageDto input: {}", dto);
        languageRepository.findByCodeAndDeletedFalse(dto.getCode())
                .ifPresent(lang -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.language.exists",
                                    new Object[]{dto.getCode()}, LocaleContextHolder.getLocale())
                    );
                });
        Language language = languageMapper.toEntity(dto);
        language.setDeleted(false);
        if (dto.getCountryId() != null && !dto.getCountryId().isEmpty()) {
            List<Country> countries = countryRepository.findAllById(dto.getCountryId());

            if (countries.isEmpty()) {
                throw new ResourceNotFoundException(
                        messageSource.getMessage("error.country.notfound.ids",
                                new Object[]{dto.getCountryId()}, LocaleContextHolder.getLocale())
                );
            }

            log.debug(">>> Countries tìm thấy: {}", countries.size());
            language.setCountries(countries);
            countries.forEach(c -> c.getLanguages().add(language));
        }
        return languageMapper.toDto(languageRepository.save(language));
    }

    @Override
    @Transactional
    public LanguageDto update(Long id, LanguageDto dto) {
        Language language = languageRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.language.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));

        // Kiểm tra duplicate code (trừ chính nó)
        languageRepository.findByCodeAndDeletedFalse(dto.getCode())
                .filter(l -> !l.getId().equals(id))
                .ifPresent(l -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.language.exists",
                                    new Object[]{dto.getCode()}, LocaleContextHolder.getLocale())
                    );
                });
        language.setCode(dto.getCode());
        language.setName(dto.getName());

        // cập nhật lại quan hệ với country nếu có
        if (dto.getCountryId() != null && !dto.getCountryId().isEmpty()) {
            List<Country> countries = countryRepository.findAllById(dto.getCountryId());
            if (countries.isEmpty()) {
                throw new ResourceNotFoundException(
                        messageSource.getMessage("error.country.notfound.ids",
                                new Object[]{dto.getCountryId()}, LocaleContextHolder.getLocale())
                );
            }
            language.setCountries(countries);
        }
        return languageMapper.toDto(languageRepository.save(language));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Language language = languageRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.language.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
        language.setDeleted(true);
        languageRepository.save(language);
    }

    @Override
    @Transactional(readOnly = true)
    public LanguageDto getById(Long id) {
        return languageRepository.findByIdAndDeletedFalse(id)
                .map(languageMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.language.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LanguageDto> getAll(Pageable pageable) {
        return languageRepository.findAllByDeletedFalse(pageable)
                .map(languageMapper::toDto);
    }

    @Override
    public List<LanguageDto> getLanguagesByCountry(Long countryId) {
        return languageRepository.findByCountries_Id(countryId).stream()
                .map(languageMapper::toDto)
                .toList();
    }
}
