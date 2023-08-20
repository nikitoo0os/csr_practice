package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.entity.api.ReportData;
import com.vyatsu.practiceCSR.repository.ReportDataRepository;
import com.vyatsu.practiceCSR.repository.ReportRepository;
import com.vyatsu.practiceCSR.service.api.ReportDataService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportDataServiceImpl implements ReportDataService {
    private final ReportDataRepository reportDataRepository;
    private final ReportRepository reportRepository;

    @Override
    public List<ReportData> getReportDataByReportId(Long id) {
        return reportDataRepository.findByReportId(id);
    }

    @Override
    public void saveReportData(List<ReportData> reportData) {
        reportDataRepository.saveAll(reportData);
    }

    @Override
    public void createReportFromPrevious(Report reportFrom, Report reportTo) {
        List<ReportData> fromReportData = reportDataRepository.findByReportId(Long.valueOf(reportFrom.getId()));
        List<ReportData> toReportData = new ArrayList<>();

        if(reportFrom.getTemplate() == reportTo.getTemplate()){
            for(ReportData reportData : fromReportData){
                ReportData reportData1 = new ReportData();
                reportData1.setService(reportData.getService());
                reportData1.setReport(reportTo);
                reportData1.setCount1(reportData.getCount1());
                reportData1.setCount2(reportData.getCount2());
                reportData1.setPercent1(reportData1.getPercent1());
                reportData1.setPercent2(reportData1.getPercent2());
                reportData1.setRegularAct(reportData1.getRegularAct());

                toReportData.add(reportData1);
            }
            reportDataRepository.saveAll(toReportData);
        }
    }

}
