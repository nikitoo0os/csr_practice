package com.vyatsu.practiceCSR.dto.helper;

import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.dto.api.ServiceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSummaryReportDTO {
    private ServiceDTO service;
    private ReportDTO report;
    private Double count1;
    private Double count2;
    private BigDecimal percent1;
    private BigDecimal percent2;
}
