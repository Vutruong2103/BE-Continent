package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.EthnicGroup;
import com.example.continent.application.domain.repository.EthnicGroupRepository;
import com.example.continent.application.domain.service.EthnicGroupService;
import com.example.continent.application.dto.EthnicGroupDto;
import com.example.continent.application.exception.DuplicateResourceException;
import com.example.continent.application.exception.ResourceNotFoundException;
import com.example.continent.application.mapper.EthnicGroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EthnicGroupServiceImpl implements EthnicGroupService {

    private final EthnicGroupRepository ethnicGroupRepository;
    private final EthnicGroupMapper ethnicGroupMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public EthnicGroupDto create(EthnicGroupDto dto) {
        // Kiểm tra trùng code
        ethnicGroupRepository.findByCodeAndDeletedFalse(dto.getCode())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.ethnic.exists",
                                    new Object[]{dto.getCode()}, LocaleContextHolder.getLocale())
                    );
                });

        EthnicGroup group = ethnicGroupMapper.toEntity(dto);
        group.setDeleted(false);
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

        // Kiểm tra trùng code (trừ chính nó)
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
}
