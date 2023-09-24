package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.ReportDataDTO;
import com.vyatsu.practiceCSR.entity.api.ReportData;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportDataMapper {
    List<ReportDataDTO> toReportDataDTO(List<ReportData> reportData);
    List<ReportData> toReportData(List<ReportDataDTO> reportDataDTOs);
}
