package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    boolean existsByName(String name);
    @Query("SELECT s FROM Service s WHERE s.isActive = true")
    List<Service> findAllActive();
}
