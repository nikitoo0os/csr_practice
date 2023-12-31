package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.entity.api.Region;
import com.vyatsu.practiceCSR.mapper.RegionMapper;
import com.vyatsu.practiceCSR.service.api.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/regions")
public class RegionController {

    private final RegionService regionService;
    private final RegionMapper regionMapper;

    @GetMapping
    private ResponseEntity<List<RegionDTO>> getAllRegions(){
        List<RegionDTO> regionList = regionService.getALlRegionsDTO();
        return ResponseEntity.ok(regionList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> getRegionById(@PathVariable Long id) {
        Region region = regionService.getRegionById(id);
        RegionDTO regionDTO = regionMapper.toRegionDTO(region);
        return ResponseEntity.ok(regionDTO);
    }

}
