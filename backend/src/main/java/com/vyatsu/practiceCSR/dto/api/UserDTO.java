package com.vyatsu.practiceCSR.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vyatsu.practiceCSR.entity.api.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

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
    private RegionDTO region;
}
