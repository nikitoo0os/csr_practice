package com.vyatsu.practiceCSR.service.api;

import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.entity.api.ReportData;

import java.util.List;

public interface ReportDataService {
    List<ReportData> getReportDataByReportId(Long id);

    void saveReportData(List<ReportData> reportData);

    Report createReportFromPrevious(Report reportFrom);
}
