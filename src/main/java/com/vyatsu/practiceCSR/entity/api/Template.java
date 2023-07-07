package com.vyatsu.practiceCSR.entity.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Setter
    @OneToMany(mappedBy = "template")
    private Set<TemplateData> templateData = new LinkedHashSet<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "template")
    private Set<Report> reports = new LinkedHashSet<>();

}