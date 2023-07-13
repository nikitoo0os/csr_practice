package com.vyatsu.practiceCSR.service.api;

import com.vyatsu.practiceCSR.entity.api.UserReport;

import java.util.List;

public interface UserReportService {
    List<UserReport> getUserReportsByUserId(Long userId);
}

