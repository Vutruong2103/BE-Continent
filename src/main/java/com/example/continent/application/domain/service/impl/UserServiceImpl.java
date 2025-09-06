package com.example.continent.application.domain.service.impl;

import com.example.continent.application.domain.model.Continent;
import com.example.continent.application.domain.model.Role;
import com.example.continent.application.domain.model.User;
import com.example.continent.application.domain.repository.RoleRepository;
import com.example.continent.application.domain.repository.UserRepository;
import com.example.continent.application.domain.service.UserService;
import com.example.continent.application.dto.ContinentDto;
import com.example.continent.application.dto.UserDto;
import com.example.continent.application.exception.DuplicateResourceException;
import com.example.continent.application.exception.ResourceNotFoundException;
import com.example.continent.application.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public UserDto create(UserDto dto) {
        // Kiểm tra trùng username
        userRepository.findByUsernameAndDeletedFalse(dto.getUsername())
                .ifPresent(u -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.user.exists",
                                    new Object[]{dto.getUsername()}, LocaleContextHolder.getLocale())
                    );
                });

        User user = userMapper.toEntity(dto);

        // set roles nếu có
//        if (dto.getRoleId() != null && !dto.getRoleId().isEmpty()) {
//            List<Role> roles = roleRepository.findAllById(dto.getRoleId());
//            if (roles.isEmpty()) {
//                throw new ResourceNotFoundException(
//                        messageSource.getMessage("error.role.notfound", null, LocaleContextHolder.getLocale())
//                );
//            }
//            user.setRoles(roles);
//        }

        user.setDeleted(false); // mặc định chưa bị xóa
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserDto update(Long id, UserDto dto) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.user.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));

        // Kiểm tra trùng username (trừ chính nó)
        userRepository.findByUsernameAndDeletedFalse(dto.getUsername())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> {
                    throw new DuplicateResourceException(
                            messageSource.getMessage("error.user.exists",
                                    new Object[]{dto.getUsername()}, LocaleContextHolder.getLocale())
                    );
                });

        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        // cập nhật roles nếu có
//        if (dto.get() != null && !dto.getRoleId().isEmpty()) {
//            List<Role> roles = roleRepository.findAllById(dto.getRoleId());
//            if (roles.isEmpty()) {
//                throw new ResourceNotFoundException(
//                        messageSource.getMessage("error.role.notfound", null, LocaleContextHolder.getLocale())
//                );
//            }
//            user.setRoles(roles);
//        }

        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.user.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
        user.setDeleted(true); // xóa mềm
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getById(Long id) {
        return userRepository.findByIdAndDeletedFalse(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(
                        messageSource.getMessage("error.user.notfound",
                                new Object[]{id}, LocaleContextHolder.getLocale())
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> getAll(Pageable pageable) {
        return userRepository.findAllByDeletedFalse(pageable)
                .map(userMapper::toDTO);
    }

    @Override
    public List<UserDto> searchByName(String keyword) {
        List<User> user;
        if(keyword==null || keyword.isBlank()){
            user = userRepository.findAll();
        }else {
            user = userRepository.findByUsernameContainingIgnoreCase(keyword);
        }
        return user.stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> u= userRepository.findByUsernameAndDeletedFalse(username);
        if(u.isPresent()){
            return  u.get();
        }
//        return u.map(userMapper::toDTO).orElse(null);
        return null;
    }

//
//
//    public void test(){
//        UserDto dto = new UserDto();
//
//        Long idSet = (long)10;
//        UserDto userDto = UserDto.builder().id((long)10).build();
//        UserDto userDto1 = UserDto.builder().id((long)11).build();
//        UserDto userDto2 = UserDto.builder().id((long)12).build();
//        UserDto userDto3 = UserDto.builder().id((long)13).build();
//        UserDto userDto4 = UserDto.builder().id((long)14).build();
//        UserDto userDto5 = UserDto.builder().id((long)15).build();
//        UserDto userDto6 = UserDto.builder().id((long)16).build();
//
//        List<UserDto> dtoList = Arrays.asList(userDto,userDto1 ,userDto2,userDto3 ,userDto4,userDto5 ,userDto6);
//        List<Integer> so = Arrays.asList(10,2 ,33,4 ,59,69,0);
//        Set<Integer> so2 =  new HashSet<>();
//        Set<Integer> so3 =  new HashSet<>();
//        so2.add(1);
//        so2.add(2);
//        so2.add(3);
//
//        so2.equals(so3);
//
//        int a = 9;
//        int b = 7;
//
//        Integer c = 9; UserDto dto2 = new UserDto();
//        Integer d = 10;
//
//        Integer[] n = new Integer[10];
//
//        dtoList.get(0);
//        so2.
//
//
//        dtoList.stream().filter(id-> id.equals(idSet)).findFirst().orElse(null);
//
//        Collections.sort(so);
//
//
//



}
