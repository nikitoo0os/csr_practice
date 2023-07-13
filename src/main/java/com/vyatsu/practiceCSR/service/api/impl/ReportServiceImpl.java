package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.dto.api.ReportDTO;
import com.vyatsu.practiceCSR.entity.api.Region;
import com.vyatsu.practiceCSR.entity.api.Report;
import com.vyatsu.practiceCSR.entity.api.User;
import com.vyatsu.practiceCSR.entity.api.UserReport;
import com.vyatsu.practiceCSR.mapper.ReportMapper;
import com.vyatsu.practiceCSR.mapper.UserMapper;
import com.vyatsu.practiceCSR.repository.RegionRepository;
import com.vyatsu.practiceCSR.repository.ReportRepository;
import com.vyatsu.practiceCSR.repository.UserReportRepository;
import com.vyatsu.practiceCSR.repository.UserRepository;
import com.vyatsu.practiceCSR.service.api.RegionService;
import com.vyatsu.practiceCSR.service.api.ReportService;
import com.vyatsu.practiceCSR.service.scheduling.TaskSchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UserReportRepository userReportRepository;
    private final ReportMapper reportMapper;
    private final UserMapper userMapper;
    private final TaskSchedulingService taskSchedulingService;
    private final RegionService regionService;
    @Override
    public Report createReport(ReportDTO reportDTO) {
        Report report = reportMapper.toReport(reportDTO);
        report.setIsActive(true);
        report.setIsCompleted(false);

        LocalDate currentDateTime = LocalDate.now();
        report.setStartDate(currentDateTime);

        report = reportRepository.save(report);

        if (report.getFrequency() != null) {
            LocalDate endDate = report.getStartDate().plusDays(report.getActiveDays());
            report.setEndDate(endDate);
            report = reportRepository.save(report);
            return extensionReport(report);
        }
        return report;
    }

    private Report report1;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;

    @Override
    public Report extensionReport(Report report) {

        taskSchedulingService.scheduleATask(
                report,
                () -> report1 = reportRun(report)
        );
        return report1;
    }

    public Report reportRun(Report report) {
        if(report.getIsActive() && !report.getIsCompleted()) {
            //дата окончания старого отчета + дни активности отчета
            LocalDate endDate = report.getEndDate().plusDays(report.getActiveDays());

            LocalDate currentDateTime = LocalDate.now();

            report.setId(null);
            report.setStartDate(currentDateTime);
            report.setEndDate(endDate);
            report.setIsCompleted(false);
            report.setIsActive(true);

            report.setIsCompleted(true);
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
        for(Integer id : usersId){
            UserReport userReport = new UserReport();
            userReport.setReport(report);
            userReport.setUser(userRepository.findById(Long.valueOf(id)).get());
            userReportRepository.save(userReport);
        }
    }

}
