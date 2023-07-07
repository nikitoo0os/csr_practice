package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api._UserDTO;
import com.vyatsu.practiceCSR.dto.auth.SignUpDto;
import com.vyatsu.practiceCSR.dto.auth.UserDto;
import com.vyatsu.practiceCSR.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<_UserDTO>> getAllUsers(){
        List<_UserDTO> userDTOList = userService.getAllUsersDTO();
        return ResponseEntity.ok(userDTOList);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody SignUpDto user){
        UserDto createdUser = userService.register(user);
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }

}
