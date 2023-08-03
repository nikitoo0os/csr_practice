package com.vyatsu.practiceCSR.entity.api;

import jakarta.persistence.*;
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

    @ManyToOne
    @Getter
    @Setter
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @ManyToOne
    @Getter
    @Setter
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

//    @Getter
//    @Setter
//    @Column(name = "frequency")
//    private Long frequency;

    @Getter
    @Setter
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Getter
    @Setter
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

//    @Getter
//    @Setter
//    @Column(name = "active_days")
//    private Integer activeDays;

    @Getter
    @Setter
    @Column(name = "comment", length = 2000)
    private String comment;
    @Getter
    @Setter
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    @Getter
    @Setter
    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;
    @Getter
    @Setter
    @OneToMany(mappedBy = "report")
    private Set<ReportData> reportData = new LinkedHashSet<>();

}