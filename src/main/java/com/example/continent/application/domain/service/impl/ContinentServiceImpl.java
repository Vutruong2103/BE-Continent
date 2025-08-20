package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.Continent;
import com.example.continent.application.domain.repository.ContinentRepository;
import com.example.continent.application.domain.service.ContinentService;
import com.example.continent.application.dto.ContinentDto;
import com.example.continent.application.mapper.ContinentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContinentServiceImpl implements ContinentService {

    private final ContinentRepository continentRepository;
    private final ContinentMapper continentMapper;

    @Override
    public ContinentDto create(ContinentDto dto) {
        Continent continent = continentMapper.toEntity(dto);
        return continentMapper.toDto(continentRepository.save(continent));
    }

    @Override
    public ContinentDto update(Long id, ContinentDto dto) {
        Continent continent = continentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Continent not found"));
        continent.setCode(dto.getCode());
        continent.setName(dto.getName());
        return continentMapper.toDto(continentRepository.save(continent));
    }

    @Override
    public void delete(Long id) {
        continentRepository.deleteById(id);
    }

    @Override
    public ContinentDto getById(Long id) {
        return continentRepository.findById(id)
                .map(continentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Continent not found"));
    }

    @Override
    public List<ContinentDto> getAll() {
        return continentRepository.findAll()
                .stream()
                .map(continentMapper::toDto)
                .collect(Collectors.toList());
    }
}
