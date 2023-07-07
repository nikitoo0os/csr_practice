package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/regions")
    private ResponseEntity<List<RegionDTO>> getAllRegions(){
        List<RegionDTO> regionList = regionService.getALlRegionsDTO();
        return ResponseEntity.ok(regionList);
    }

}
