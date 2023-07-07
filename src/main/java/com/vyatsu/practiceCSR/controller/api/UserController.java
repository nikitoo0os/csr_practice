package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api._UserDTO;
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
    public ResponseEntity<List<_UserDTO>> getAllUsers(){
        List<_UserDTO> userDTOList = userService.getAllUsersDTO();
        return ResponseEntity.ok(userDTOList);
    }


}
