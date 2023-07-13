package com.vyatsu.practiceCSR.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {

    @NotEmpty
    private String surname;

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String patronymic;

    @NotEmpty
    private String email;

    @NotEmpty
    private char[] password;

    @NotEmpty
    private Long region_id;

}
