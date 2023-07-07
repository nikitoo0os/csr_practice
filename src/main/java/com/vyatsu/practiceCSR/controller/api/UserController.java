package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.auth.UserDto;
import com.vyatsu.practiceCSR.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userDTOList = userService.getAllUsers();
        return ResponseEntity.ok(userDTOList);
    }


}
