package com.example.continent.domain.service.impl;

import com.example.continent.application.dto_.ContinentDto;
import com.example.continent.application.exception_.ResourceAlreadyExistsException;
import com.example.continent.application.exception_.ResourceNotFoundException;
import com.example.continent.application.mapper_.ContinentMapper;
import com.example.continent.domain.model_.Continent;
import com.example.continent.domain.repository_.ContinentRepository;
import com.example.continent.domain.service.ContinentService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * map DTO → Entity → save DB → convert lại trả về DTO -> client
 * nơi xử lý logic các method từ servie chuyển cho controller sử dụng
 * quản lý logic liên quan đến Continent như Crud, tìm kiếm, chuyển đổi giữa DTO và Entity, xử lý ngoại lệ và hỗ trợ i18n cho thông báo lỗi.
 *
 */

@Service
@RequiredArgsConstructor
@Transactional
public class ContinentServiceImpl implements ContinentService {

    private final ContinentRepository continentRepository;
    private final ContinentMapper continentMapper;
    private final MessageSource messageSource;


    @Override
    public ContinentDto create(ContinentDto dto) {
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
        continent.setDeleted(true);
        continentRepository.save(continent);
    }

    @Override
    public List<ContinentDto> searchByName(String keyword) {
        List<Continent> contients;
        if(keyword==null || keyword.isBlank()){
            contients = continentRepository.findAll();
        }else {
            contients = continentRepository.findByNameContainingIgnoreCase(keyword);
        }
        return contients.stream()
                .map(continentMapper::toDto)
                .toList();
    }
}
