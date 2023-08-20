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
        List<ReportData> toReportData = reportDataRepository.findByReportId(Long.valueOf(reportTo.getId()));

        if(reportFrom.getTemplate() == reportTo.getTemplate()){
            for(int i = 0; i < fromReportData.stream().count(); i++){
                if(fromReportData.get(i).getService().getName().equals(toReportData.get(i).getService().getName())){
                    toReportData.get(i).setCount1(fromReportData.get(i).getCount1());
                    toReportData.get(i).setCount2(fromReportData.get(i).getCount2());
                    toReportData.get(i).setPercent1(fromReportData.get(i).getPercent1());
                    toReportData.get(i).setPercent2(fromReportData.get(i).getPercent2());
                    toReportData.get(i).setRegularAct(fromReportData.get(i).getRegularAct());
                }
            }
            reportDataRepository.saveAll(toReportData);
        }
    }

}
