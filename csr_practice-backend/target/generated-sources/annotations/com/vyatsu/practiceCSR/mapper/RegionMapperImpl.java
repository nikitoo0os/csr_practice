package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.entity.api.Region;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-13T13:23:31+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class RegionMapperImpl implements RegionMapper {

    @Override
    public RegionDTO toRegionDTO(Region regionList) {
        if ( regionList == null ) {
            return null;
        }

        RegionDTO.RegionDTOBuilder regionDTO = RegionDTO.builder();

        if ( regionList.getId() != null ) {
            regionDTO.id( regionList.getId() );
        }
        regionDTO.name( regionList.getName() );

        return regionDTO.build();
    }

    @Override
    public List<RegionDTO> toListRegionDTO(List<Region> regionList) {
        if ( regionList == null ) {
            return null;
        }

        List<RegionDTO> list = new ArrayList<RegionDTO>( regionList.size() );
        for ( Region region : regionList ) {
            list.add( toRegionDTO( region ) );
        }

        return list;
    }
}
