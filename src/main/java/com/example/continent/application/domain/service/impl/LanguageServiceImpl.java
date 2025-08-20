package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.Country;
import com.example.continent.application.domain.model.Language;
import com.example.continent.application.domain.repository.CountryRepository;
import com.example.continent.application.domain.repository.LanguageRepository;
import com.example.continent.application.domain.service.LanguageService;
import com.example.continent.application.dto.LanguageDto;
import com.example.continent.application.mapper.LanguageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;
    private final CountryRepository countryRepository;

//    @Override
//    public LanguageDto create(LanguageDto dto) {
//        Language language = languageMapper.toEntity(dto);
//
//        if (dto.getCountryId() != null && !dto.getCountryId().isEmpty()) {
//            List<Country> countries = countryRepository.findAllById(dto.getCountryId());
//            language.setCountries(countries);
//        }
//
//        return languageMapper.toDto(languageRepository.save(language));
//    }

    /*
     * Lưu ngôn ngữ + lưu đất nước.
     */

    @Override
    @Transactional
    public LanguageDto create(LanguageDto dto) {
        log.debug(">>> LanguageDto tìm thấy: {}",dto);

        Language language = languageMapper.toEntity(dto);
        Optional<Language> found = languageRepository.findByCodeAndDeletedFalse(language.getCode());
        if(found.isPresent()) {
            log.debug(">>> Ngôn ngữ đã tồn tại: {}", found.get());
            throw new RuntimeException("Language with code " + language.getCode() + " already exists");
        }


        List<Country> countries = new ArrayList<>();
        List<Language> languages = new ArrayList<>();
        languages.add(language);
        languageRepository.saveAll(languages);
//        Language saved = languageRepository.save(language);
        if (dto.getCountryId() != null && !dto.getCountryId().isEmpty()) { // Kiểm tra nếu countryId không null và không rỗng
            countries = countryRepository.findAllById(dto.getCountryId());
            log.debug(">>> Countries tìm thấy: {}",countries.size());
            language.setCountries(countries);
            countries.forEach(country->country.setLanguages(languages));
            countryRepository.saveAll(countries);
        }



//        System.out.println(">>> Saved: " + saved);

        return languageMapper.toDto(language);
    }

    @Override
    public LanguageDto update(Long id, LanguageDto dto) {
        Language language = languageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Language not found"));
        language.setCode(dto.getCode());
        language.setName(dto.getName());
        return languageMapper.toDto(languageRepository.save(language));
    }

    @Override
    public void delete(Long id) {
        languageRepository.deleteById(id);
    }

    @Override
    public LanguageDto getById(Long id) {
        return languageRepository.findById(id)
                .map(languageMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Language not found"));
    }

    @Override
    public List<LanguageDto> getAll() {
        return languageRepository.findAll()
                .stream()
                .map(languageMapper::toDto)
                .collect(Collectors.toList());
    }
}

