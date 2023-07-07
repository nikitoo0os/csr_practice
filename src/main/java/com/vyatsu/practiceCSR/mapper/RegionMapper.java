package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.entity.api.RegionList;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegionMapper {
    RegionDTO toRegionDTO(RegionList regionList);

    List<RegionDTO> toListRegionDTO(List<RegionList> regionList);
}
