package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.config.auth.UserAuthenticationProvider;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.entity.api.ReportData;
import com.vyatsu.practiceCSR.logger.LoggerCSR;
import com.vyatsu.practiceCSR.logger.enumDebugLog;
import com.vyatsu.practiceCSR.repository.ReportDataRepository;
import com.vyatsu.practiceCSR.repository.ReportRepository;
import com.vyatsu.practiceCSR.service.api.ReportDataService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportDataServiceImpl implements ReportDataService {
    private final ReportDataRepository reportDataRepository;
    private final ReportRepository reportRepository;
    private final UserAuthenticationProvider authenticationProvider;

    @Override
    public List<ReportData> getReportDataByReportId(Long id) {
        return reportDataRepository.findByReportId(id);
    }

    @Override
    public void saveReportData(String token, List<ReportData> reportData) {
        String jwtToken = token.substring(7);
        Authentication authentication = authenticationProvider.validateToken(jwtToken);
        Long userId = ((UserAuthDto) authentication.getPrincipal()).getId();
        reportDataRepository.saveAll(reportData);

        LoggerCSR.createDebugMsg(enumDebugLog.SAVE_REPORT, userId, Long.valueOf(reportData.get(0).getReport().getId()));
    }

    @Override
    public void createReportFromPrevious(Report reportFrom, Report reportTo) {
        List<ReportData> fromReportData = reportDataRepository.findByReportId(Long.valueOf(reportFrom.getId()));
        List<ReportData> toReportData = reportDataRepository.findByReportId(Long.valueOf(reportTo.getId()));

        if(reportFrom.getTemplate() == reportTo.getTemplate()){
            for(int i = 0; i < (long) fromReportData.size(); i++){
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
        LoggerCSR.createDebugMsg(enumDebugLog.COPY_REPORT, Long.valueOf(reportFrom.getId()), Long.valueOf(reportTo.getId()));
    }

}
