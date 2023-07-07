package com.vyatsu.practiceCSR.service.impl;

import com.vyatsu.practiceCSR.dto.api._UserDTO;
import com.vyatsu.practiceCSR.dto.auth.CredentialsDto;
import com.vyatsu.practiceCSR.dto.auth.SignUpDto;
import com.vyatsu.practiceCSR.dto.auth.UserDto;
import com.vyatsu.practiceCSR.exception.AppException;
import com.vyatsu.practiceCSR.mapper.UserMapper;
import com.vyatsu.practiceCSR.repository.RegionRepository;
import com.vyatsu.practiceCSR.repository.UserRepository;
import com.vyatsu.practiceCSR.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.vyatsu.practiceCSR.entity.api.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;
    private final RegionRepository regionRepository;

    @Override
    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        user.setRegion(regionRepository.findById(userDto.getRegion_id())
                .orElseThrow(() -> new AppException("Регион с таким идентификатором не найден", HttpStatus.BAD_REQUEST)));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toListUserDTO(users);
    }

    @Override
    public List<_UserDTO> getAllUsersDTO() {
        List<User> users = userRepository.findAll();
        List<_UserDTO> userDTOList = new ArrayList<>();
        for(User user : users){
            _UserDTO userDTO = new _UserDTO();
            userDTO.setFullName(user.getSurname() + " " + user.getFirstName().charAt(0) + ". " + user.getPatronymic().charAt(0));
            userDTO.setEmail(user.getEmail());
            userDTO.setRegion(regionRepository.findById(Long.valueOf(user.getRegion().getId())).get().getName());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto create(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        user.setRegion(regionRepository.findById(userDto.getRegion_id())
                .orElseThrow(() -> new AppException("Регион с таким идентификатором не найден", HttpStatus.BAD_REQUEST)));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

}