package com.vyatsu.practiceCSR.dto.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionsSummaryReportDTO {
        private LocalDate startDate;
        private LocalDate endDate;
        private Long templateId;
}
