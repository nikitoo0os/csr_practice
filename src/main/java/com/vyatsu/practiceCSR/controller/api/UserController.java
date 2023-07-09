package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api._UserDTO;
import com.vyatsu.practiceCSR.dto.auth.SignUpDto;
import com.vyatsu.practiceCSR.dto.auth.UserDto;
import com.vyatsu.practiceCSR.entity.api.User;
import com.vyatsu.practiceCSR.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/users/&{id}")
    public HttpStatus dropUser(@PathVariable Long id){
        userService.deleteById(id);
        return HttpStatus.OK;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setIsActive(true);
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }


}
