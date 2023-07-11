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
    private Template template;
    private Region region;
    private Long frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer activeDays;
    private String comment;
    private Boolean isActive;
    private Boolean isCompleted;


    private Long regionId;
}
