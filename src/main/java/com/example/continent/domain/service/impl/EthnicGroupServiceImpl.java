package com.example.continent.domain.service.impl;

import com.example.continent.application.dto_.EthnicGroupDto;
import com.example.continent.application.exception_.DuplicateResourceException;
import com.example.continent.application.exception_.ResourceNotFoundException;
import com.example.continent.application.mapper_.EthnicGroupMapper;
import com.example.continent.domain.model_.Country;
import com.example.continent.domain.model_.EthnicGroup;
import com.example.continent.domain.repository_.CountryRepository;
import com.example.continent.domain.repository_.EthnicGroupRepository;
import com.example.continent.domain.service.EthnicGroupService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EthnicGroupServiceImpl implements EthnicGroupService {

    private final EthnicGroupRepository ethnicGroupRepository;
    private final EthnicGroupMapper ethnicGroupMapper;
    private final MessageSource messageSource;
    private final CountryRepository countryRepository;

    @Override
    @Transactional
    public EthnicGroupDto create(EthnicGroupDto dto) {
        log.debug(">>> EthnicGroupDto input: {}", dto);
        ethnicGroupRepository.findByCodeAndDeletedFalse(dto.getCode())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.ethnic.exists",
                                    new Object[]{dto.getCode()}, LocaleContextHolder.getLocale())
                    );
                });

        EthnicGroup group = ethnicGroupMapper.toEntity(dto);
        group.setDeleted(false); // ? Vì sao phải check false ở đây vậy em chai ? sao không để mặc định là false ở DTO luôn hoặc là ép client truyền xuống DB là false luôn ? 
        if (dto.getCountryId() != null && !dto.getCountryId().isEmpty()) {
            List<Country> countries = countryRepository.findAllById(dto.getCountryId());

            if (countries.isEmpty()) {
                throw new ResourceNotFoundException(
                        messageSource.getMessage("error.country.notfound.ids",
                                new Object[]{dto.getCountryId()}, LocaleContextHolder.getLocale())
                );
            }

            log.debug(">>> Countries tìm thấy: {}", countries.size());
            group.setCountries(countries);
            countries.forEach(c -> c.getEthnicGroups().add(group));
        }
        return ethnicGroupMapper.toDto(ethnicGroupRepository.save(group));
    }

    @Override
    @Transactional
    public EthnicGroupDto update(Long id, EthnicGroupDto dto) {
        EthnicGroup group = ethnicGroupRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.ethnic.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));

        ethnicGroupRepository.findByCodeAndDeletedFalse(dto.getCode())
                .filter(g -> !g.getId().equals(id))
                .ifPresent(g -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.ethnic.exists",
                                    new Object[]{dto.getCode()}, LocaleContextHolder.getLocale())
                    );
                });

        group.setCode(dto.getCode());
        group.setName(dto.getName());

        return ethnicGroupMapper.toDto(ethnicGroupRepository.save(group));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        EthnicGroup group = ethnicGroupRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.ethnic.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
        group.setDeleted(true); // xóa mềm
        ethnicGroupRepository.save(group);
    }

    @Override
    @Transactional(readOnly = true)
    public EthnicGroupDto getById(Long id) {
        return ethnicGroupRepository.findByIdAndDeletedFalse(id)
                .map(ethnicGroupMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.ethnic.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EthnicGroupDto> getAll(Pageable pageable) {
        return ethnicGroupRepository.findAllByDeletedFalse(pageable)
                .map(ethnicGroupMapper::toDto);
    }

    @Override
    public List<EthnicGroupDto> getEthnicGroupsByCountry(Long countryId) {
        return ethnicGroupRepository.findByCountries_Id(countryId).stream()
                .map(ethnicGroupMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EthnicGroupDto> getByContinent(Long continentId) {
        List<EthnicGroup> ethnicGroups = ethnicGroupRepository.findByContinentId(continentId);
        return ethnicGroups.stream()
                .map(ethnicGroupMapper::toDto)
                .collect(Collectors.toList());
    }
}
