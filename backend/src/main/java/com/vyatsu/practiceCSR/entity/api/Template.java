package com.vyatsu.practiceCSR.entity.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "template")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "date", nullable = false, columnDefinition = "timestamp without time zone default current_timestamp")
    private Timestamp date;

    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Setter
    @Column(name = "count_all_requests", nullable = false)
    private String countAllRequests;

    @Getter
    @Setter
    @Column(name = "count_epgu_requests", nullable = false)
    private String countEPGURequests;

    @Getter
    @Setter
    @Column(name = "percent_epgu_requests", nullable = false)
    private String percentEPGURequests;

    @Getter
    @Setter
    @Column(name = "percent_not_violation_epgu_requests", nullable = false)
    private String percentNotViolationEPGURequests;

    @Getter
    @Setter
    @OneToMany(mappedBy = "template")
    private Set<TemplateData> templateData = new LinkedHashSet<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "template")
    private Set<Report> reports = new LinkedHashSet<>();

}