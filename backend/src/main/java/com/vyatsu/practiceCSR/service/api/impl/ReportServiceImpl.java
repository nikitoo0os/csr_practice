package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.config.auth.UserAuthenticationProvider;
import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.dto.helper.CreateReportDTO;
import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.entity.api.ReportData;
import com.vyatsu.practiceCSR.entity.api.TemplateData;
import com.vyatsu.practiceCSR.entity.api.User;
import com.vyatsu.practiceCSR.logger.LoggerCSR;
import com.vyatsu.practiceCSR.logger.enumDebugLog;
import com.vyatsu.practiceCSR.logger.enumInfoLog;
import com.vyatsu.practiceCSR.mapper.RegionMapper;
import com.vyatsu.practiceCSR.mapper.ReportMapper;
import com.vyatsu.practiceCSR.repository.ReportDataRepository;
import com.vyatsu.practiceCSR.repository.ReportRepository;
import com.vyatsu.practiceCSR.repository.TemplateDataRepository;
import com.vyatsu.practiceCSR.repository.UserRepository;
import com.vyatsu.practiceCSR.service.api.ReportService;
import com.vyatsu.practiceCSR.utils.XLSUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final RegionMapper regionMapper;
    private final TemplateDataRepository templateDataRepository;
    private final UserRepository userRepository;
    private final ReportDataRepository reportDataRepository;
    private final UserAuthenticationProvider authenticationProvider;

    @Override
    public void createReport(String token, CreateReportDTO createReportDTO) {
        String jwtToken = token.substring(7);
        Authentication authentication = authenticationProvider.validateToken(jwtToken);
        Long userId = ((UserAuthDto) authentication.getPrincipal()).getId();

        List<RegionDTO> regions = createReportDTO.getRegions()
                .stream()
                .collect(Collectors.toMap(RegionDTO::getName, Function.identity(), (existing, replacement) -> existing))
                .values()
                .stream()
                .toList();

        for(RegionDTO regionDTO : createReportDTO.getRegions()) {
            Report report = reportMapper.toReport(createReportDTO);
            report.setIsActive(true);
            report.setRegion(regionMapper.toRegion(regionDTO));
            report.setStartDate(LocalDate.now());
            report = reportRepository.save(report);

            List<TemplateData> templateDataList = templateDataRepository.findListByTemplateId(Long.valueOf(report.getTemplate().getId()));
            List<ReportData> reportDataList = new ArrayList<>();
            for (TemplateData templateData : templateDataList) {
                ReportData reportData = new ReportData();
                reportData.setReport(report);
                reportData.setService(templateData.getService());
                reportDataList.add(reportData);
            }
            reportDataRepository.saveAll(reportDataList);
            LoggerCSR.createDebugMsg(enumDebugLog.CREATE_REPORT, userId, Long.valueOf(report.getId()));
        }
    }

    @Override
    public Report getReportById(Long id) {
        Report report = reportRepository.findById(id).get();
        LocalDate currentDate = LocalDate.now();
        boolean currentDateIsAfterEndDate = currentDate.isAfter(report.getEndDate());
        if(currentDateIsAfterEndDate){
            report.setIsActive(false);
        }
        return report;
    }

    @Override
    public List<Report> getActiveReportByUserId(Long userId) {
        User user = userRepository.findById(userId).get();
        List<Report> activeReports = reportRepository.findActiveReportsByRegionId(Long.valueOf(user.getRegion().getId()));
        return activeReports;
    }

    @Override
    public List<Report> getInactiveReportByUserId(Long userId) {
        User user = userRepository.findById(userId).get();
        List<Report> inactiveReports = reportRepository.findInactiveReportByRegionId(Long.valueOf(user.getRegion().getId()));
        return inactiveReports;
    }

    @Override
    public void createReportsUser(Long reportId, List<Integer> usersId) {
        Report report = getReportById(reportId);
//        for(Integer id : usersId){
//            UserReport userReport = new UserReport();
//            userReport.setReport(report);
//            userReport.setUser(userRepository.findById(Long.valueOf(id)).get());
//            userReportRepository.save(userReport);
//        }
    }

    @Override
    public void updateStatusToEnd(String token, Long reportId) {
        String jwtToken = token.substring(7);
        Authentication authentication = authenticationProvider.validateToken(jwtToken);
        Long userId = ((UserAuthDto) authentication.getPrincipal()).getId();

        Report report = reportRepository.findById(reportId).get();
        report.setIsActive(false);
        reportRepository.save(report);
        LocalDate currentLocalDate = LocalDate.now();
        if(currentLocalDate.isAfter(report.getEndDate())){
            LoggerCSR.createDebugMsg(enumDebugLog.COMPLETE_REPORT, userId, Long.valueOf(report.getId()));
        }
        else{
            LoggerCSR.createDebugMsg(enumDebugLog.COMPLETE_TIMEOUT_REPORT, userId, Long.valueOf(report.getId()));
        }
    }

    @Override
    public HttpEntity<ByteArrayResource> getResultReportData(String token, LocalDate localDateFrom, LocalDate localDateTo, Long templateId) throws IOException {
        String jwtToken = token.substring(7);
        Authentication authentication = authenticationProvider.validateToken(jwtToken);
        Long userId = ((UserAuthDto) authentication.getPrincipal()).getId();

        List<Report> reports = reportRepository.findByTemplateId(templateId);

        //фильтруем по дате
        reports = reports.stream()
                .filter(report -> !report.getStartDate().isBefore(localDateFrom) &&
                        !report.getStartDate().isAfter(localDateTo))
                .collect(Collectors.toList());

        //получаем данные этих отчетов
        List<List<ReportData>> lastReportData = new ArrayList<>();
        for(Report report : reports){
            lastReportData.add(reportDataRepository.findByReportId(Long.valueOf(report.getId())));
        }

        List<ReportData> resultReportData = new ArrayList<>();
        if(lastReportData.size() > 0){
            resultReportData = lastReportData.get(0);
        }

        for(int i = 0; i < resultReportData.size(); i++){
            for(int j = 0; j < lastReportData.size(); j++){
                var count1 = resultReportData.get(i).getCount1();
                if(count1 == null){
                    count1 = 0;
                }
                var count2 = resultReportData.get(i).getCount2();
                if(count2 == null){
                    count2 = 0;
                }

                var percent1_ = resultReportData.get(i).getPercent1();
                if(percent1_ == null){
                    if(count1 > 0){
                        BigDecimal percent1 = BigDecimal.valueOf(count2).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(count1), 10, RoundingMode.HALF_UP);
                        resultReportData.get(i).setPercent1(percent1);
                    }
                }
                var percent2_ = resultReportData.get(i).getPercent2();
                if(percent2_ == null){
                    if(count2 > 0){
                        BigDecimal percent2 = BigDecimal.valueOf(count2).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(count2), 10, RoundingMode.HALF_UP);
                        resultReportData.get(i).setPercent2(percent2);
                    }
                }

                var lastCount1 = lastReportData.get(j).get(i).getCount1();
                if(lastCount1 == null){
                    lastCount1 = 0;
                }
                var lastCount2 = lastReportData.get(j).get(i).getCount2();
                if(lastCount2 == null){
                    lastCount2 = 0;
                }

                resultReportData.get(i).setCount1(count1 + lastCount1);
                resultReportData.get(i).setCount2(count2 + lastCount2);
            }
        }
        byte[] excelContent = XLSUtil.writeReportDataToByteArray(resultReportData,
                new String[]{
                        "Наименование услуги в Кировской области",
                        "Количество обращений за отчетный период с учетом всех способов подачи (нарастающим итогом с 01.01.2023 по 31.07.2023)",
                        "Количество обращений, поступивших в эл виде через ЕПГУ (нарастающим итогом с 01.01.2023 по 31.07.2023)",
                        "% обращений в эл виде через ЕПГУ (целевой показатель на 2023 год 40%) (нарастающим итогом с 01.01.2023 по 31.07.2023)",
                        "Доля услуг, предоставленных без нарушения регламентного срока при оказании услуги через ЕПГУ (нарастающим итогом с 01.01.2023 по 31.07.2023) (%)"
                });

        ByteArrayResource resource = new ByteArrayResource(excelContent);

        try (FileOutputStream outputStream = new FileOutputStream("C:/example.xlsx")) {
            byte[] data = new byte[Math.toIntExact(resource.contentLength())];
            resource.getInputStream().read(data);
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoggerCSR.createInfoMsg(enumInfoLog.GENERATE_SUMMARY_REPORT, userId, templateId);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=file.xlsx")
                .body(resource);
    }

    @Override
    public boolean isLastMonth(Report report) {
        YearMonth currentYearMonth = YearMonth.from(LocalDate.now());
        YearMonth prevYearMonth = currentYearMonth.minusMonths(1);
        YearMonth reportYearMonth = YearMonth.from(report.getStartDate());

        return reportYearMonth.equals(prevYearMonth);
    }

}
