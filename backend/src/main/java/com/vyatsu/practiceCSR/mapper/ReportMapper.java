package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.entity.api.Report;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    ReportDTO toReportDTO(Report report);
    Report toReport(ReportDTO reportDTO);

    List<ReportDTO> toListReportDTO(List<Report> reports);
}
