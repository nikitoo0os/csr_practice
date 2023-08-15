package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.entity.api.Region;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegionMapper {
    RegionDTO toRegionDTO(Region regionList);

    Region toRegion(RegionDTO regionDTO);
    List<RegionDTO> toListRegionDTO(List<Region> regionList);
}
