package com.vyatsu.practiceCSR.dto.api;

import jakarta.persistence.Column;
import lombok.*;

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
