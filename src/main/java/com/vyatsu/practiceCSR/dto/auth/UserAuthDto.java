package com.vyatsu.practiceCSR.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthDto {

    private Long id;
    private String firstname;
    private String surname;
    private String email;
    private String token;

}