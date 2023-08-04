package com.vyatsu.practiceCSR.dto.api;

import com.vyatsu.practiceCSR.entity.api.Region;
import com.vyatsu.practiceCSR.entity.api.Template;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDTO {
    private Integer id;
    private TemplateDTO template;
    private RegionDTO region;
    private LocalDate startDate;
    private LocalDate endDate;
    private String comment;
    private Boolean isActive;
    private Long regionId;
}
