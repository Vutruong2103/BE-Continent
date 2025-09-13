package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.Country;
import com.example.continent.application.domain.model.EthnicGroup;
import com.example.continent.application.domain.repository.CountryRepository;
import com.example.continent.application.domain.repository.EthnicGroupRepository;
import com.example.continent.application.domain.service.EthnicGroupService;
import com.example.continent.application.dto.EthnicGroupDto;
import com.example.continent.application.exception.DuplicateResourceException;
import com.example.continent.application.exception.ResourceNotFoundException;
import com.example.continent.application.mapper.EthnicGroupMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * create(EthnicGroupDto dto): Tạo một nhóm dân tộc mới, kiểm tra trùng mã (code), chuyển đổi DTO sang Entity, lưu vào cơ sở dữ liệu và trả về DTO đã lưu.
 * update(Long id, EthnicGroupDto dto): Cập nhật thông tin nhóm dân tộc theo ID, nếu không tìm thấy sẽ ném ngoại lệ ResourceNotFoundException.
 * delete(Long id): Xóa mềm nhóm dân tộc theo ID (chỉ đánh dấu deleted = true), nếu không tìm thấy sẽ ném ngoại lệ ResourceNotFoundException.
 * getById(Long id): Lấy thông tin nhóm dân tộc theo ID, nếu không tìm thấy sẽ ném ngoại lệ ResourceNotFoundException.
 * getAll(Pageable pageable): Lấy danh sách tất cả nhóm dân tộc chưa bị xóa mềm với phân trang.
 * getEthnicGroupsByCountry(Long countryId): Lấy danh sách nhóm dân tộc theo ID quốc gia.
 * getByContinent(Long continentId): Lấy danh sách nhóm dân tộc theo ID lục địa.
 */
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
        group.setDeleted(false);

        if (dto.getCountryIds() != null && !dto.getCountryIds().isEmpty()) {
            List<Country> countries = countryRepository.findAllById(dto.getCountryIds());
            if (countries.isEmpty()) {
                throw new ResourceNotFoundException(
                        messageSource.getMessage("error.country.notfound.ids",
                                new Object[]{dto.getCountryIds()}, LocaleContextHolder.getLocale())
                );
            }
            group.setCountries(countries);
        }

        EthnicGroup saved = ethnicGroupRepository.save(group);
        return ethnicGroupRepository.findByIdAndDeletedFalse(saved.getId())
                .map(ethnicGroupMapper::toDto)
                .orElseThrow();
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

        ethnicGroupMapper.updateFromDto(dto, group);

        if (dto.getCountryIds() != null) {
            if (!dto.getCountryIds().isEmpty()) {
                List<Country> countries = countryRepository.findAllById(dto.getCountryIds());
                if (countries.isEmpty()) {
                    throw new ResourceNotFoundException(
                            messageSource.getMessage("error.country.notfound.ids",
                                    new Object[]{dto.getCountryIds()}, LocaleContextHolder.getLocale())
                    );
                }
                group.setCountries(countries);
            } else {
                group.setCountries(List.of());
            }
        }

        EthnicGroup saved = ethnicGroupRepository.save(group);
        return ethnicGroupRepository.findByIdAndDeletedFalse(saved.getId())
                .map(ethnicGroupMapper::toDto)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        EthnicGroup group = ethnicGroupRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.ethnic.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
        group.setDeleted(true);
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
    @Transactional(readOnly = true)
    public List<EthnicGroupDto> getEthnicGroupsByCountry(Long countryId) {
        return ethnicGroupRepository.findByCountries_Id(countryId).stream()
                .map(ethnicGroupMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EthnicGroupDto> getByContinent(Long continentId) {
        return ethnicGroupRepository.findByContinentId(continentId).stream()
                .map(ethnicGroupMapper::toDto)
                .toList();
    }
}
