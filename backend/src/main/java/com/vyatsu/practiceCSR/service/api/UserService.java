package com.vyatsu.practiceCSR.service.api;

import com.vyatsu.practiceCSR.dto.api.UserDTO;
import com.vyatsu.practiceCSR.dto.auth.CredentialsDto;
import com.vyatsu.practiceCSR.dto.auth.SignUpDto;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.entity.api.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserAuthDto login(CredentialsDto credentialsDto);

    UserAuthDto register(String token, SignUpDto userDto);

    List<UserAuthDto> getAllUsers();

    List<UserDTO> getAllUsersDTO();

    UserAuthDto findByEmail(String email);

    UserAuthDto create(SignUpDto userDto);

    void deleteById(String token, Long id);

    ResponseEntity<Void> softDeleteById(Long id);

    User getUserById(Long id);
    void updateUser(User user);


    User getUserByEmail(String email);

    List<User> getUserByRegionId(Long id);

    void changePassword(User user, String password);
}
