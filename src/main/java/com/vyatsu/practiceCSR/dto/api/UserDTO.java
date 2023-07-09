package com.vyatsu.practiceCSR.dto.api;

import com.vyatsu.practiceCSR.entity.api.Region;
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
    private String fullName;
    private String email;
    private Region region;
}
