package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.entity.api.Report;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    ReportDTO toReportDTO(Report report);
    Report toReport(ReportDTO reportDTO);
}
