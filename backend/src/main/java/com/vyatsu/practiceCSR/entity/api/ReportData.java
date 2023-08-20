package com.vyatsu.practiceCSR.entity.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "report_data")
public class ReportData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @Getter
    @Setter
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    @JoinColumn(name = "report_id")
    private Report report;
    @Getter
    @Setter
    @Column(name = "count_1")
    private Integer count1;

    @Getter
    @Setter
    @Column(name = "count_2")
    private Integer count2;

    @Getter
    @Setter
    @Column(name = "percent_1", precision = 3, scale = 1)
    private BigDecimal percent1;

    @Getter
    @Setter
    @Column(name = "percent_2", precision = 3, scale = 1)
    private BigDecimal percent2;
    @Getter
    @Setter
    @Column(name = "regular_act", length = Integer.MAX_VALUE)
    private String regularAct;

}