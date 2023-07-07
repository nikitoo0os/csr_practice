package com.vyatsu.practiceCSR.entity.api;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    @JoinColumn(name = "template_id")
    private Template template;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    @JoinColumn(name = "region_id")
    private Region region;

    @NotNull
    @Getter
    @Setter
    @Column(name = "frequency", nullable = false)
    private Long frequency;

    @Getter
    @Setter
    @Column(name = "start_date")
    private LocalDate startDate;
    @Getter
    @Setter
    @Column(name = "end_date")
    private LocalDate endDate;

    @Getter
    @Setter
    @Column(name = "active_days")
    private Integer activeDays;

    @Getter
    @Setter
    @Column(name = "comment", length = Integer.MAX_VALUE)
    private String comment;
    @Getter
    @Setter
    @Column(name = "is_active")
    private Boolean isActive;
    @Getter
    @Setter
    @Column(name = "is_completed")
    private Boolean isCompleted;
    @Getter
    @Setter
    @OneToMany(mappedBy = "report")
    private Set<UserReport> userReports = new LinkedHashSet<>();
    @Getter
    @Setter
    @OneToMany(mappedBy = "report")
    private Set<ReportData> reportData = new LinkedHashSet<>();

}