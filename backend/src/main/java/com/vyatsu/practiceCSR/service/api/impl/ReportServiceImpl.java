package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.config.auth.UserAuthenticationProvider;
import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.dto.helper.CreateReportDTO;
import com.vyatsu.practiceCSR.dto.helper.CreateSummaryReportDTO;
import com.vyatsu.practiceCSR.dto.helper.OptionsSummaryReportDTO;
import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.entity.api.ReportData;
import com.vyatsu.practiceCSR.entity.api.TemplateData;
import com.vyatsu.practiceCSR.entity.api.User;
import com.vyatsu.practiceCSR.logger.EnumWarnLog;
import com.vyatsu.practiceCSR.logger.LoggerCSR;
import com.vyatsu.practiceCSR.mapper.RegionMapper;
import com.vyatsu.practiceCSR.mapper.ReportMapper;
import com.vyatsu.practiceCSR.mapper.ServiceMapper;
import com.vyatsu.practiceCSR.repository.ReportDataRepository;
import com.vyatsu.practiceCSR.repository.ReportRepository;
import com.vyatsu.practiceCSR.repository.TemplateDataRepository;
import com.vyatsu.practiceCSR.repository.UserRepository;
import com.vyatsu.practiceCSR.service.api.ReportService;
import com.vyatsu.practiceCSR.utils.XLSUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final XLSUtil Util;
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final RegionMapper regionMapper;
    private final TemplateDataRepository templateDataRepository;
    private final UserRepository userRepository;
    private final ReportDataRepository reportDataRepository;
    private final UserAuthenticationProvider authenticationProvider;
    private final ServiceMapper serviceMapper;

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

        for (RegionDTO regionDTO : createReportDTO.getRegions()) {
            Report report = reportMapper.toReport(createReportDTO);
            report.setIsActive(true);
            report.setRegion(regionMapper.toRegion(regionDTO));
            report.setStartDate(LocalDate.now());
            report = reportRepository.save(report);

            List<TemplateData> templateDataList = templateDataRepository
                    .findListByTemplateId(Long.valueOf(report.getTemplate().getId()));
            List<ReportData> reportDataList = new ArrayList<>();
            for (TemplateData templateData : templateDataList) {
                ReportData reportData = new ReportData();
                reportData.setReport(report);
                reportData.setService(templateData.getService());
                reportDataList.add(reportData);
            }
            reportDataRepository.saveAll(reportDataList);
            LoggerCSR.createWarnMsg(EnumWarnLog.CREATE_REPORT, userId, Long.valueOf(report.getId()));
        }
    }

    @Override
    public Report getReportById(Long id) {
        Report report = reportRepository.findById(id).get();
        LocalDate currentDate = LocalDate.now();
        boolean currentDateIsAfterEndDate = currentDate.isAfter(report.getEndDate());
        if (currentDateIsAfterEndDate) {
            report.setIsActive(false);
        }
        return report;
    }

    @Override
    public List<Report> getActiveReportByUserId(Long userId) {
        User user = userRepository.findById(userId).get();
        List<Report> activeReports = reportRepository
                .findActiveReportsByRegionId(Long.valueOf(user.getRegion().getId()));
        return activeReports;
    }

    @Override
    public List<Report> getInactiveReportByUserId(Long userId) {
        User user = userRepository.findById(userId).get();
        List<Report> inactiveReports = reportRepository
                .findInactiveReportByRegionId(Long.valueOf(user.getRegion().getId()));
        return inactiveReports;
    }

    @Override
    public void createReportsUser(Long reportId, List<Integer> usersId) {
        Report report = getReportById(reportId);
        // for(Integer id : usersId){
        // UserReport userReport = new UserReport();
        // userReport.setReport(report);
        // userReport.setUser(userRepository.findById(Long.valueOf(id)).get());
        // userReportRepository.save(userReport);
        // }
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
        if (currentLocalDate.isAfter(report.getEndDate())) {
            LoggerCSR.createWarnMsg(EnumWarnLog.COMPLETE_REPORT, userId, Long.valueOf(report.getId()));
        } else {
            LoggerCSR.createWarnMsg(EnumWarnLog.COMPLETE_TIMEOUT_REPORT, userId, Long.valueOf(report.getId()));
        }
    }

    private List<com.vyatsu.practiceCSR.entity.api.Service> getUniqueSeviceList(List<ReportData> data){
        List<com.vyatsu.practiceCSR.entity.api.Service> services = new ArrayList<>();
        for(ReportData reportData : data){
            if(!services.contains(reportData.getService())){
                services.add(reportData.getService());
            }
        }
        return services;
    }

    @Override
    public List<CreateSummaryReportDTO> getResultReportData(String token, OptionsSummaryReportDTO options) throws IOException {
        String jwtToken = token.substring(7);
        Authentication authentication = authenticationProvider.validateToken(jwtToken);
        Long userId = ((UserAuthDto) authentication.getPrincipal()).getId();

        List<Report> reports = reportRepository.findByTemplateId(options.getTemplateId());

        // фильтруем по дате
        reports = reports.stream()
                .filter(report -> !report.getStartDate().isBefore(options.getStartDate()) &&
                        !report.getStartDate().isAfter(options.getEndDate()))
                .collect(Collectors.toList());

        // получаем данные этих отчетов
        List<ReportData> lastReportData = new ArrayList<>();
        for (Report report : reports) {
            List<ReportData> reportDataList = reportDataRepository.findByReportId(Long.valueOf(report.getId()));
            for(ReportData reportData : reportDataList){
                lastReportData.add(reportData);
            }
        }

        List<CreateSummaryReportDTO> result = new ArrayList<>();
        List<com.vyatsu.practiceCSR.entity.api.Service> serviceList = getUniqueSeviceList(lastReportData);

        int tempCount1 = 0, tempCount2 = 0;
        BigDecimal tempPercent1 = new BigDecimal(0), tempPercent2 = new BigDecimal(0);
        String tempRegularAct = null;
        com.vyatsu.practiceCSR.entity.api.Service tempService = null;
        for(com.vyatsu.practiceCSR.entity.api.Service service : serviceList){
            CreateSummaryReportDTO createSummaryReportDTO = new CreateSummaryReportDTO();
            for(ReportData reportData : lastReportData){
                if(service == reportData.getService()){
                    if(reportData.getCount1() != null){
                        tempCount1 += reportData.getCount1();
                    }
                    if(reportData.getCount2() != null){
                        tempCount2 += reportData.getCount2();
                    }
                    if(tempRegularAct == null){
                        tempRegularAct = reportData.getRegularAct();
                    }
                    createSummaryReportDTO.setService(serviceMapper.serviceToServiceDTO(service));
                    createSummaryReportDTO.setReport(reportMapper.toReportDTO(reportData.getReport()));
                }
            }
            tempPercent1 = BigDecimal.valueOf(tempCount2 * 100L / tempCount1);

            createSummaryReportDTO.setCount1(tempCount1);
            createSummaryReportDTO.setCount2(tempCount2);
            createSummaryReportDTO.setPercent1(tempPercent1);
            createSummaryReportDTO.setPercent2(tempPercent2);

            result.add(createSummaryReportDTO);
        }

//        ByteArrayInputStream in = Util.createXLSX(result,
//                new String[] {
//                        "№",
//                        "Наименование услуги в Кировской области",
//                        "Количество обращений за отчетный период с учетом всех способов подачи (нарастающим итогом с 01.01.2023 по 31.07.2023)",
//                        "Количество обращений, поступивших в эл виде через ЕПГУ (нарастающим итогом с 01.01.2023 по 31.07.2023)",
//                        "% обращений в эл виде через ЕПГУ (целевой показатель на 2023 год 40%) (нарастающим итогом с 01.01.2023 по 31.07.2023)",
//                        "Доля услуг, предоставленных без нарушения регламентного срока при оказании услуги через ЕПГУ (нарастающим итогом с 01.01.2023 по 31.07.2023) (%)"
//                },
//                response);

        LoggerCSR.createWarnMsg(EnumWarnLog.GENERATE_SUMMARY_REPORT, userId, options.getTemplateId());

        return result;
    }

    @Override
    public boolean isLastMonth(Report report) {
        YearMonth currentYearMonth = YearMonth.from(LocalDate.now());
        YearMonth prevYearMonth = currentYearMonth.minusMonths(1);
        YearMonth reportYearMonth = YearMonth.from(report.getStartDate());

        return reportYearMonth.equals(prevYearMonth);
    }

    @Override
    public List<Report> getCompletedReportsByTemplateId(Long id) {
        return reportRepository.findCompletedReportsByTemplateId(id);
    }

}
