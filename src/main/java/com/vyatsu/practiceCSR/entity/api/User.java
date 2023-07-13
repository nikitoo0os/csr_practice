package com.vyatsu.practiceCSR.entity.api;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "user_")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Getter
    @Setter
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 255)
    @NotNull
    @Getter
    @Setter
    @Column(name = "password", nullable = false)
    private String password;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @Getter
    @Setter
    @Size(max = 100)
    @Column(name = "surname", length = 100, nullable = false)
    private String surname;

    @Getter
    @Setter
    @Size(max = 100)
    @Column(name = "firstname", length = 100)
    private String firstname;

    @Getter
    @Setter
    @Size(max = 100)
    @Column(name = "patronymic", length = 100)
    private String patronymic;


    @Getter
    @Setter
    @Column(name = "is_admin", columnDefinition = "boolean default false")
    private Boolean isAdmin;

    @Getter
    @Setter
    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @Getter
    @Setter
    @OneToMany(mappedBy = "user")
    private Set<UserReport> userReports = new LinkedHashSet<>();

}