package com.vyatsu.practiceCSR.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    private String firstname;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
    private RegionDTO region;
}
