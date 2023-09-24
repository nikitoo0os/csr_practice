package com.vyatsu.practiceCSR.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemplateDTO {
    private int id;
    private Timestamp date;
    private String name;
    private String countAllRequests;
    private String countEPGURequests;
    private String percentEPGURequests;
    private String percentNotViolationEPGURequests;
}
