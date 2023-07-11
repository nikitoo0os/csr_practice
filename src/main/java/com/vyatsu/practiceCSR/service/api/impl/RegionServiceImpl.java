package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.entity.api.Region;
import com.vyatsu.practiceCSR.mapper.RegionMapper;
import com.vyatsu.practiceCSR.repository.RegionRepository;
import com.vyatsu.practiceCSR.service.api.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    @Override
    public List<RegionDTO> getALlRegionsDTO() {
        List<Region> regionList = regionRepository.findAll();
        return regionMapper.toListRegionDTO(regionList);
    }

    @Override
    public Region getRegionById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Region not found with id: " + id));
    }
}
