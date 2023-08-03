package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RegionRepository extends JpaRepository<Region, Long> {


}
