package com.vyatsu.practiceCSR.repository;

import com.vyatsu.practiceCSR.entity.api.RegionList;
import com.vyatsu.practiceCSR.entity.api.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<RegionList, Long> {

}
