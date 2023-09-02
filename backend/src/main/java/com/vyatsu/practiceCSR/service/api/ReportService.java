package com.vyatsu.practiceCSR.service.api;

import com.vyatsu.practiceCSR.dto.helper.CreateReportDTO;
import com.vyatsu.practiceCSR.dto.helper.CreateSummaryReportDTO;
import com.vyatsu.practiceCSR.dto.helper.OptionsSummaryReportDTO;
import com.vyatsu.practiceCSR.entity.api.Report;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface ReportService {
    void createReport(String token, CreateReportDTO createReportDTO);

    Report getReportById(Long id);

    List<Report> getActiveReportByUserId(Long userId);

    List<Report> getInactiveReportByUserId(Long userId);

    void createReportsUser(Long reportId, List<Integer> userDTOs);

    void updateStatusToEnd(String token, Long reportId);

    List<CreateSummaryReportDTO> getResultReportData(String token, OptionsSummaryReportDTO options) throws IOException;

    boolean isLastMonth(Report reportFrom);

    List<Report> getCompletedReportsByTemplateId(Long id);
}
