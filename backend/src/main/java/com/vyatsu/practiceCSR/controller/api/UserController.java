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
import java.util.List;

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
    public ResponseEntity<UserAuthDto> createUser(@RequestBody SignUpDto user){
        UserAuthDto createdUser = userService.register(user);
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }

    @PutMapping("/&{id}")
    public ResponseEntity<Void> dropUser(@PathVariable Long id){
        userService.deleteById(id);
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
        if(userDTO.getPassword() != null && !user.getPassword().equals(userDTO.getPassword())){
            user.setPassword(userDTO.getPassword());
        }
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable Long id){
        return userService.softDeleteById(id);
    }


}
