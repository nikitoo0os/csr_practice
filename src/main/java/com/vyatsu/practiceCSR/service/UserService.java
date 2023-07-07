package com.vyatsu.practiceCSR.service;

import com.vyatsu.practiceCSR.dto.api._UserDTO;
import com.vyatsu.practiceCSR.dto.auth.CredentialsDto;
import com.vyatsu.practiceCSR.dto.auth.SignUpDto;
import com.vyatsu.practiceCSR.dto.auth.UserDto;

import java.util.List;

public interface UserService {
    public UserDto login(CredentialsDto credentialsDto);

    UserDto register(SignUpDto userDto);

    List<UserDto> getAllUsers();

    List<_UserDTO> getAllUsersDTO();
}
