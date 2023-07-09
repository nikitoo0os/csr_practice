package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    boolean existsByName(String name);
}
