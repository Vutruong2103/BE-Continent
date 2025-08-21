package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.EthnicGroup;
import com.example.continent.application.domain.repository.EthnicGroupRepository;
import com.example.continent.application.domain.service.EthnicGroupService;
import com.example.continent.application.dto.EthnicGroupDto;
import com.example.continent.application.exception.ResourceNotFoundException;
import com.example.continent.application.mapper.EthnicGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EthnicGroupServiceImpl implements EthnicGroupService {

    private final EthnicGroupRepository ethnicGroupRepository;
    private final EthnicGroupMapper ethnicGroupMapper;

    @Override
    public EthnicGroupDto create(EthnicGroupDto dto) {
        EthnicGroup group = ethnicGroupMapper.toEntity(dto);
        return ethnicGroupMapper.toDto(ethnicGroupRepository.save(group));
    }

    @Override
    public EthnicGroupDto update(Long id, EthnicGroupDto dto) {
        EthnicGroup group = ethnicGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EthnicGroup with id " + id + " not found"));
        group.setCode(dto.getCode());
        group.setName(dto.getName());
        return ethnicGroupMapper.toDto(ethnicGroupRepository.save(group));
    }

    @Override
    public void delete(Long id) {
        EthnicGroup group = ethnicGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("EthnicGroup with id " + id + " not found"));
        ethnicGroupRepository.delete(group);
    }

    @Override
    public EthnicGroupDto getById(Long id) {
        return ethnicGroupRepository.findById(id)
                .map(ethnicGroupMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("EthnicGroup with id " + id + " not found"));
    }

    @Override
    public List<EthnicGroupDto> getAll() {
        return ethnicGroupRepository.findAll()
                .stream()
                .map(ethnicGroupMapper::toDto)
                .collect(Collectors.toList());
    }
}
