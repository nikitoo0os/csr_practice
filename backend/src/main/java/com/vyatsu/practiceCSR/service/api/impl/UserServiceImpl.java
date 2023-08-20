package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.dto.api.UserDTO;
import com.vyatsu.practiceCSR.dto.auth.CredentialsDto;
import com.vyatsu.practiceCSR.dto.auth.SignUpDto;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.entity.api.Region;
import com.vyatsu.practiceCSR.exception.AppException;
import com.vyatsu.practiceCSR.mapper.RegionMapper;
import com.vyatsu.practiceCSR.mapper.UserMapper;
import com.vyatsu.practiceCSR.repository.RegionRepository;
import com.vyatsu.practiceCSR.repository.UserRepository;
import com.vyatsu.practiceCSR.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.vyatsu.practiceCSR.entity.api.User;
import org.springframework.http.ResponseEntity;
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
    private final RegionMapper regionMapper;
    private final RegionRepository regionRepository;

    @Override
    public UserAuthDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserAuthDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserAuthDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));

        user.setIsAdmin(false);
        user.setIsActive(true);

        user.setRegion(regionRepository.findById(userDto.getRegion_id())
                .orElseThrow(() -> new AppException("Регион с таким идентификатором не найден", HttpStatus.BAD_REQUEST)));

        User savedUser = userRepository.save(user);

        return userMapper.toUserAuthDto(savedUser);
    }

    @Override
    public List<UserAuthDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toListUserAuthDTO(users);
    }

    @Override
    public List<UserDTO> getAllUsersDTO() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user : users){
            if(user.getIsActive() && !user.getIsAdmin()){
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setFirstname(user.getFirstname());
                userDTO.setSurname(user.getSurname());
                userDTO.setPatronymic(user.getPatronymic());
                userDTO.setEmail(user.getEmail());
                Region region = regionRepository.findById(Long.valueOf(user.getRegion().getId())).get();
                RegionDTO regionDTO = regionMapper.toRegionDTO(region);
                userDTO.setRegion(regionDTO);
                userDTOList.add(userDTO);
            }
        }
        return userDTOList;
    }

    @Override
    public UserAuthDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserAuthDto(user);
    }

    @Override
    public UserAuthDto create(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
        user.setIsActive(true);
        user.setIsAdmin(false);
        user.setRegion(regionRepository.findById(userDto.getRegion_id())
                .orElseThrow(() -> new AppException("Регион с таким идентификатором не найден", HttpStatus.BAD_REQUEST)));

        User savedUser = userRepository.save(user);

        return userMapper.toUserAuthDto(savedUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<Void> softDeleteById(Long id) {
        User user = getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setIsActive(false);
        updateUser(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void updateUser(User user) {
        if(user != null){
            userRepository.save(user);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public List<User> getUserByRegionId(Long id) {
        return userRepository.findByRegionId(id);
    }

    @Override
    public void changePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(password)));
        updateUser(user);
    }

}