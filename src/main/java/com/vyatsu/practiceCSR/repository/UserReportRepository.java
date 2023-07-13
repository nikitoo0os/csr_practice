package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {
}
