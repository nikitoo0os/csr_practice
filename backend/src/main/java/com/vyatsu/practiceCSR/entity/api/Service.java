package com.vyatsu.practiceCSR.entity.api;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Getter
    @Setter
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Getter
    @Setter
    @Column(name = "is_active")
    private Boolean isActive;

    @Getter
    @Setter
    @OneToMany(mappedBy = "service")
    private Set<TemplateData> templateData = new LinkedHashSet<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private Set<ReportData> reportData = new LinkedHashSet<>();

}