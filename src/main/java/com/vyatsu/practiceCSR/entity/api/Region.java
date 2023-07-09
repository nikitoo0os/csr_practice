package com.vyatsu.practiceCSR.entity.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "region_list")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    private String name;

    @Getter
    @Setter
    @OneToMany( mappedBy = "region")
    private Set<User> users = new LinkedHashSet<>();

    @Getter
    @Setter
    @OneToMany(mappedBy = "region")
    private Set<Report> reports = new LinkedHashSet<>();

}