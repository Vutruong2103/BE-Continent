package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.Continent;
import com.example.continent.application.domain.repository.ContinentRepository;
import com.example.continent.application.domain.service.ContinentService;
import com.example.continent.application.dto.ContinentDto;
import com.example.continent.application.exception.ResourceAlreadyExistsException;
import com.example.continent.application.exception.ResourceNotFoundException;
import com.example.continent.application.mapper.ContinentMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContinentServiceImpl implements ContinentService {

    private final ContinentRepository continentRepository;
    private final ContinentMapper continentMapper;

    @Override
    public ContinentDto create(ContinentDto dto) {
        // Check duplicate code
        if (continentRepository.existsByCode(dto.getCode())) {
            throw new ResourceAlreadyExistsException("Continent with code " + dto.getCode() + " already exists");
        }
        Continent continent = continentMapper.toEntity(dto);
        return continentMapper.toDto(continentRepository.save(continent));
    }

    @Override
    public ContinentDto getById(Long id) {
        Continent continent = continentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Continent with id " + id + " not found"));
        return continentMapper.toDto(continent);
    }

    @Override
    public List<ContinentDto> getAll() {
        return continentRepository.findAll()
                .stream().map(continentMapper::toDto).toList();
    }

    @Override
    public ContinentDto update(Long id, ContinentDto dto) {
        Continent continent = continentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Continent with id " + id + " not found"));

        continent.setName(dto.getName());
        continent.setCode(dto.getCode());

        return continentMapper.toDto(continentRepository.save(continent));
    }

    @Override
    public void delete(Long id) {
        Continent continent = continentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Continent with id " + id + " not found"));

        continentRepository.delete(continent);
    }
}
