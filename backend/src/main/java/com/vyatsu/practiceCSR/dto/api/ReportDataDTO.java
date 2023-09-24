package com.vyatsu.practiceCSR.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDataDTO {
    private Integer id;
    private ServiceDTO service;
    private ReportDTO report;
    private Integer count1;
    private Integer count2;
    private BigDecimal percent1;
    private BigDecimal percent2;
    private String regularAct;
}
