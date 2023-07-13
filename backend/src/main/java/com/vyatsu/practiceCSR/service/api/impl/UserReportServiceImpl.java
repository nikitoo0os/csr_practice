package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.entity.api.UserReport;
import com.vyatsu.practiceCSR.repository.UserReportRepository;
import com.vyatsu.practiceCSR.service.api.UserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReportServiceImpl implements UserReportService {

    private final UserReportRepository userReportRepository;

    @Autowired
    public UserReportServiceImpl(UserReportRepository userReportRepository) {
        this.userReportRepository = userReportRepository;
    }

    @Override
    public List<UserReport> getUserReportsByUserId(Long userId) {
        return userReportRepository.findByUserId(userId);
    }
}