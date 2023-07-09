package com.vyatsu.practiceCSR.service;

import com.vyatsu.practiceCSR.dto.api.UserDTO;
import com.vyatsu.practiceCSR.dto.auth.CredentialsDto;
import com.vyatsu.practiceCSR.dto.auth.SignUpDto;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.entity.api.User;

import java.util.List;

public interface UserService {
    public UserAuthDto login(CredentialsDto credentialsDto);

    UserAuthDto register(SignUpDto userDto);

    List<UserAuthDto> getAllUsers();

    List<UserDTO> getAllUsersDTO();

    UserAuthDto findByEmail(String email);

    UserAuthDto create(SignUpDto userDto);

    void deleteById(Long id);

    User getUserById(Long id);
    void updateUser(User user);
}
