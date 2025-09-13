package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.Continent;
import com.example.continent.application.domain.repository.ContinentRepository;
import com.example.continent.application.domain.service.ContinentService;
import com.example.continent.application.dto.ContinentDto;
import com.example.continent.application.exception.DuplicateResourceException;
import com.example.continent.application.exception.ResourceNotFoundException;
import com.example.continent.application.mapper.ContinentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author : Vutq
 *
 * map DTO → Entity → save DB → convert lại trả về DTO -> client
 * nơi xử lý logic các method từ servie chuyển cho controller sử dụng
 * quản lý logic liên quan đến Continent như Crud, tìm kiếm, chuyển đổi giữa DTO và Entity, xử lý ngoại lệ và hỗ trợ i18n cho thông báo lỗi.
 *
 * @create(ContinentDto dto): Tạo một lục địa mới, kiểm tra trùng mã (code), chuyển đổi DTO sang Entity, lưu vào cơ sở dữ liệu và trả về DTO đã lưu.
 * @getById(Long id): Lấy thông tin lục địa theo ID, nếu không tìm thấy sẽ ném ngoại lệ ResourceNotFoundException.
 * @getAll(Pageable pageable): Lấy danh sách tất cả lục địa chưa bị xóa mềm với phân trang.
 * @update(Long id, ContinentDto dto): Cập nhật thông tin lục địa theo ID, nếu không tìm thấy sẽ ném ngoại lệ ResourceNotFoundException.
 * @delete(Long id): Xóa mềm lục địa theo ID (chỉ đánh dấu deleted = true), nếu không tìm thấy sẽ ném ngoại lệ ResourceNotFoundException.
 * @searchByName(String keyword): Tìm kiếm lục địa theo tên chứa từ khóa (không phân biệt hoa thường) với phân trang.
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
        continentRepository.findByCodeAndDeletedFalse(dto.getCode())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.continent.exists",
                                    new Object[]{dto.getCode()},
                                    LocaleContextHolder.getLocale())
                    );
                });

        Continent continent = continentMapper.toEntity(dto);
        continent.setDeleted(false);

        return continentMapper.toDto(continentRepository.save(continent));
    }

    @Override
    @Transactional(readOnly = true)
    public ContinentDto getById(Long id) {
        return continentRepository.findByIdAndDeletedFalse(id)
                .map(continentMapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                messageSource.getMessage("error.continent.notfound",
                                        new Object[]{id},
                                        LocaleContextHolder.getLocale())
                        ));
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

        continent.setCode(dto.getCode());
        continent.setName(dto.getName());

        continentMapper.updateFromDto(dto, continent);

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
    @Transactional(readOnly = true)
    public Page<ContinentDto> searchByName(String keyword) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Continent> continents;

        if (keyword == null || keyword.isBlank()) {
            continents = continentRepository.findAllByDeletedFalse(pageable);
        } else {
            continents = continentRepository.findByNameContainingIgnoreCaseAndDeletedFalse(keyword, pageable);
        }

        return continents.map(continentMapper::toDto);
    }

}
