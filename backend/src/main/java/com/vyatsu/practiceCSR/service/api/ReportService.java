package com.vyatsu.practiceCSR.service.api;

import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.dto.api.UserDTO;
import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.entity.api.ReportData;
import com.vyatsu.practiceCSR.entity.api.Template;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    Report createReport(ReportDTO reportDTO);

    Report extensionReport(Report report);

    Report getReportById(Long id);

    List<Report> getActiveReportByUserId(Long userId);

    List<Report> getInactiveReportByUserId(Long userId);

    void createReportsUser(Long reportId, List<Integer> userDTOs);

    void updateStatusToEnd(Long reportId);

    List<ReportData> getResultReportData(LocalDate localDateFrom, LocalDate localDateTo);

    boolean isLastMonth(Report reportFrom);
}
