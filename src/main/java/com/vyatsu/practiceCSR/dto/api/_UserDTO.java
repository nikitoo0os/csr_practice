package com.vyatsu.practiceCSR.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class _UserDTO {
    private Integer id;
    private String fullName;
    private String email;
    private String region;
}
