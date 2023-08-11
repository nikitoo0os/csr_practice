package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.entity.api.ReportData;
import com.vyatsu.practiceCSR.entity.api.TemplateData;
import com.vyatsu.practiceCSR.entity.api.User;
import com.vyatsu.practiceCSR.mapper.ReportMapper;
import com.vyatsu.practiceCSR.mapper.UserMapper;
import com.vyatsu.practiceCSR.repository.*;
import com.vyatsu.practiceCSR.service.api.RegionService;
import com.vyatsu.practiceCSR.service.api.ReportService;
import com.vyatsu.practiceCSR.service.scheduling.TaskSchedulingService;
import com.vyatsu.practiceCSR.utils.XLSUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final UserMapper userMapper;
    private final TaskSchedulingService taskSchedulingService;
    private final RegionService regionService;
    private final TemplateDataRepository templateDataRepository;
    @Override
    public Report createReport(ReportDTO reportDTO) {
        Report report = reportMapper.toReport(reportDTO);
        report.setIsActive(true);
      //  report.setIsCompleted(false);

        LocalDate currentDateTime = LocalDate.now();
        report.setStartDate(currentDateTime);
        report = reportRepository.save(report);

        List<TemplateData> templateDataList = templateDataRepository.findListByTemplateId(Long.valueOf(report.getTemplate().getId()));
        List<ReportData> reportDataList = new ArrayList<>();

        for(TemplateData templateData : templateDataList){
            ReportData reportData = new ReportData();
            reportData.setReport(report);
            reportData.setService(templateData.getService());
            reportDataList.add(reportData);
        }
        reportDataRepository.saveAll(reportDataList);

        return report;
    }

    private Report report1;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final ReportDataRepository reportDataRepository;

    @Override
    public Report extensionReport(Report report) {

        taskSchedulingService.scheduleATask(
                report,
                () -> report1 = reportRun(report)
        );
        return report1;
    }

    public Report reportRun(Report report) {
        if(report.getIsActive()) {
            //дата окончания старого отчета + дни активности отчета
            LocalDate endDate = report.getEndDate();

            LocalDate currentDateTime = LocalDate.now();

            report.setId(null);
            report.setStartDate(currentDateTime);
            report.setEndDate(endDate);
          //  report.setIsCompleted(false);
            report.setIsActive(true);

        //    report.setIsCompleted(true);
            reportRepository.save(report);

            //записываем новый(продленный старый) отчет
            System.out.println("Создан отчет");
            return reportRepository.save(report);
        }
        return report;
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
    public void updateStatusToEnd(Long reportId) {
        Report report = reportRepository.findById(reportId).get();
        report.setIsActive(false);
       // report.setIsCompleted(true);
        reportRepository.save(report);
    }

    @Override
    public HttpEntity<ByteArrayResource> getResultReportData(LocalDate localDateFrom, LocalDate localDateTo, Long templateId) throws IOException {
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

                if(count1 != 0){
                    BigDecimal percent1 = BigDecimal.valueOf(count2).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(count1), 10, RoundingMode.HALF_UP);
                    resultReportData.get(i).setPercent1(percent1);
                    // resultReportData.get(i).setPercent2(resultReportData.get(i).getPercent2().add(lastReportData.get(j).get(i).getPercent2()));
                }

            }
        }
        byte[] excelContent = XLSUtil.writeDataToByteArray(resultReportData,
                new String[]{"№",
                        "Наименование услуги в Кировской области",
                        "Количество обращений за отчетный период с учетом всех способов подачи (нарастающим итогом с 01.01.2023 по 31.07.2023)",
                        "Количество обращений, поступивших в эл виде через ЕПГУ (нарастающим итогом с 01.01.2023 по 31.07.2023)",
                        "% обращений в эл виде через ЕПГУ (целевой показатель на 2023 год 40%) (нарастающим итогом с 01.01.2023 по 31.07.2023)",
                        "Доля услуг, предоставленных без нарушения регламентного срока при оказании услуги через ЕПГУ (нарастающим итогом с 01.01.2023 по 31.07.2023) (%)"
                });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "force-download"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=filex.clsx");

        return new HttpEntity<>(new ByteArrayResource(excelContent), headers);
    }

    @Override
    public boolean isLastMonth(Report report) {
        YearMonth currentYearMonth = YearMonth.from(LocalDate.now());
        YearMonth prevYearMonth = currentYearMonth.minusMonths(1);
        YearMonth reportYearMonth = YearMonth.from(report.getStartDate());

        return reportYearMonth.equals(prevYearMonth);
    }

}
