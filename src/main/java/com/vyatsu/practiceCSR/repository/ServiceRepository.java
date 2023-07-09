package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
