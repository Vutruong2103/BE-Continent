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
 * @author : Vutq
 *
 * @getLanguagesByCountry: Lấy danh sách ngôn ngữ của một quốc gia theo ID quốc gia với phân trang.
 * @create: Tạo mới một quốc gia, kiểm tra trùng mã (code), liên kết với lục địa, lưu vào cơ sở dữ liệu và trả về DTO đã lưu.
 * @update: Cập nhật thông tin quốc gia theo ID, kiểm tra trùng mã (code), liên kết với lục địa, lưu vào cơ sở dữ liệu và trả về DTO đã cập nhật.
 * @delete: Xóa mềm quốc gia theo ID (chỉ đánh dấu deleted = true).
 * @getById: Lấy thông tin quốc gia theo ID, nếu không tìm thấy sẽ ném ngoại lệ ResourceNotFoundException.
 * @getAll: Lấy danh sách tất cả quốc gia chưa bị xóa mềm với phân trang.
 * @getCountriesByContinent: Lấy danh sách quốc gia theo ID lục địa.
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

        countryMapper.updateFromDto(dto, country);

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
        country.setDeleted(true); // xóa mềm
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
        return countryRepository.findByContinentId(continentId).stream()
                .map(countryMapper::toDto)
                .toList();
    }
}

