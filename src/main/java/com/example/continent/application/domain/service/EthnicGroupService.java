package com.example.continent.application.domain.service;

import com.example.continent.application.dto.EthnicGroupDto;
import java.util.List;

public interface EthnicGroupService {
    EthnicGroupDto create(EthnicGroupDto dto);
    EthnicGroupDto update(Long id, EthnicGroupDto dto);
    void delete(Long id);
    EthnicGroupDto getById(Long id);
    List<EthnicGroupDto> getAll();
}
