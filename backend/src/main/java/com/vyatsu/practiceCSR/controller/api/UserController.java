package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api.UserDTO;
import com.vyatsu.practiceCSR.dto.auth.SignUpDto;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.entity.api.User;
import com.vyatsu.practiceCSR.mapper.UserMapper;
import com.vyatsu.practiceCSR.service.api.RegionService;
import com.vyatsu.practiceCSR.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.CharBuffer;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RegionService regionService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> userDTOList = userService.getAllUsersDTO();
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = userMapper.toUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/region/{id}")
    public ResponseEntity<List<UserDTO>> getUserByRegionId(@PathVariable Long id){
        List<User> userList = userService.getUserByRegionId(id);
        List<UserDTO> userDTO = userMapper.toListUserDTO(userList);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserAuthDto> createUser(@RequestHeader("Authorization") String token, @RequestBody SignUpDto user){
        UserAuthDto createdUser = userService.register(token, user);
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }

    @PutMapping("/&{id}")
    public ResponseEntity<Void> dropUser(@RequestHeader("Authorization") String token, @PathVariable Long id){
        userService.deleteById(token, id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody UserDTO userDTO){
        User user = userService.getUserById((long) userDTO.getId());
        user.setSurname(userDTO.getSurname());
        user.setFirstname(userDTO.getFirstname());
        user.setPatronymic(userDTO.getPatronymic());
        user.setEmail(userDTO.getEmail());
        user.setRegion(regionService.getRegionById((long) userDTO.getRegion().getId()));
        if(!Objects.equals(userDTO.getPassword(), "") && !user.getPassword().equals(userDTO.getPassword())){
            user.setPassword(userDTO.getPassword());
            userService.changePassword(user, userDTO.getPassword());
        }
        else{
            return ResponseEntity.badRequest().build();
        }
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable Long id){
        return userService.softDeleteById(id);
    }


}
