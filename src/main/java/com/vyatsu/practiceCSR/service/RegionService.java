package com.vyatsu.practiceCSR.service;

import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.entity.api.Region;

import java.util.List;

public interface RegionService {

    public List<RegionDTO> getALlRegionsDTO();
    Region getRegionById(Long id);
}
