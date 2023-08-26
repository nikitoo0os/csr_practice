package com.vyatsu.practiceCSR.entity.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "template_data")
public class TemplateData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    private Integer id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

}