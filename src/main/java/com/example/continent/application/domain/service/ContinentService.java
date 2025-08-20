package com.example.continent.application.domain.service;

import com.example.continent.application.dto.ContinentDto;
import java.util.List;

public interface ContinentService {
    ContinentDto create(ContinentDto dto);
    ContinentDto update(Long id, ContinentDto dto);
    void delete(Long id);
    ContinentDto getById(Long id);
    List<ContinentDto> getAll();
}
