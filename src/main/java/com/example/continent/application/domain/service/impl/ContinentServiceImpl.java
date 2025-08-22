package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.Continent;
import com.example.continent.application.domain.repository.ContinentRepository;
import com.example.continent.application.domain.service.ContinentService;
import com.example.continent.application.dto.ContinentDto;
import com.example.continent.application.exception.ResourceAlreadyExistsException;
import com.example.continent.application.exception.ResourceNotFoundException;
import com.example.continent.application.mapper.ContinentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContinentServiceImpl implements ContinentService {

    private final ContinentRepository continentRepository;
    private final ContinentMapper continentMapper;
    private final MessageSource messageSource;

    @Override
    public ContinentDto create(ContinentDto dto) {
        // Check duplicate code
        if (continentRepository.existsByCode(dto.getCode())) {
            throw new ResourceAlreadyExistsException(
                    messageSource.getMessage("error.continent.exists",
                            new Object[]{dto.getCode()},
                            LocaleContextHolder.getLocale())
            );
        }
        Continent continent = continentMapper.toEntity(dto);
        return continentMapper.toDto(continentRepository.save(continent));
    }

    @Override
    @Transactional(readOnly = true)
    public ContinentDto getById(Long id) {
        Continent continent = continentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                messageSource.getMessage("error.continent.notfound",
                                        new Object[]{id},
                                        LocaleContextHolder.getLocale())
                        ));
        return continentMapper.toDto(continent);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContinentDto> getAll(Pageable pageable) {
        return continentRepository.findAllByDeletedFalse(pageable)
                .map(continentMapper::toDto);
    }

    @Override
    public ContinentDto update(Long id, ContinentDto dto) {
        Continent continent = continentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.continent.notfound",
                                new Object[]{id},
                                LocaleContextHolder.getLocale())
                ));

        continent.setName(dto.getName());
        continent.setCode(dto.getCode());

        return continentMapper.toDto(continentRepository.save(continent));
    }

    @Override
    public void delete(Long id) {
        Continent continent = continentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                messageSource.getMessage("error.continent.notfound",
                                        new Object[]{id},
                                        LocaleContextHolder.getLocale())
                        ));

        // Xóa mềm
        continent.setDeleted(true);
        continentRepository.save(continent);
    }
}
