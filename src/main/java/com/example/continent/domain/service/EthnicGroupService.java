package com.example.continent.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.continent.application.dto_.EthnicGroupDto;

import java.util.List;

public interface EthnicGroupService {
    EthnicGroupDto create(EthnicGroupDto dto);
    EthnicGroupDto update(Long id, EthnicGroupDto dto);
    void delete(Long id);
    EthnicGroupDto getById(Long id);
    Page<EthnicGroupDto> getAll(Pageable pageable);
    List<EthnicGroupDto> getEthnicGroupsByCountry(Long countryId);
    List<EthnicGroupDto> getByContinent(Long continentId);
}
