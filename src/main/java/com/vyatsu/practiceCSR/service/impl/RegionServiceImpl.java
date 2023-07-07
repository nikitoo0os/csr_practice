package com.vyatsu.practiceCSR.service.impl;

import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.entity.api.RegionList;
import com.vyatsu.practiceCSR.mapper.RegionMapper;
import com.vyatsu.practiceCSR.repository.RegionRepository;
import com.vyatsu.practiceCSR.service.RegionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    public RegionServiceImpl(RegionRepository regionRepository,
                             RegionMapper regionMapper) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
    }

    @Override
    public List<RegionDTO> getALlRegionsDTO() {
        List<RegionList> regionList = regionRepository.findAll();
        return regionMapper.toListRegionDTO(regionList);
    }
}
