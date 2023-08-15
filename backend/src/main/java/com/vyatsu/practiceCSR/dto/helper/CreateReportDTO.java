package com.vyatsu.practiceCSR.dto.helper;

import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.dto.api.TemplateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReportDTO {
        private Integer id;
        private TemplateDTO template;
        private List<RegionDTO> regions;
        private LocalDate startDate;
        private LocalDate endDate;
        private String comment;
        private Boolean isActive;
        private Long regionId;
}
