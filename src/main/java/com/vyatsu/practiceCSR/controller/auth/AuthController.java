package com.vyatsu.practiceCSR.controller.auth;

import com.vyatsu.practiceCSR.config.auth.UserAuthenticationProvider;
import com.vyatsu.practiceCSR.dto.auth.CredentialsDto;
import com.vyatsu.practiceCSR.dto.auth.SignUpDto;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserAuthDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserAuthDto userAuthDto = userService.login(credentialsDto);
        userAuthDto.setToken(userAuthenticationProvider.createToken(credentialsDto.getEmail()));
        return ResponseEntity.ok(userAuthDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserAuthDto> register(@RequestBody @Valid SignUpDto user) {
        UserAuthDto createdUser = userService.register(user);
        createdUser.setToken(userAuthenticationProvider.createToken(user.getEmail()));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }

}
