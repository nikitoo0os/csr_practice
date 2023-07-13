package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.ServiceDTO;
import com.vyatsu.practiceCSR.entity.api.Service;
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
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public ServiceDTO serviceToServiceDTO(Service service) {
        if ( service == null ) {
            return null;
        }

        ServiceDTO.ServiceDTOBuilder serviceDTO = ServiceDTO.builder();

        if ( service.getId() != null ) {
            serviceDTO.id( service.getId() );
        }
        serviceDTO.name( service.getName() );

        return serviceDTO.build();
    }

    @Override
    public List<ServiceDTO> listServiceToListServiceDTO(List<Service> serviceList) {
        if ( serviceList == null ) {
            return null;
        }

        List<ServiceDTO> list = new ArrayList<ServiceDTO>( serviceList.size() );
        for ( Service service : serviceList ) {
            list.add( serviceToServiceDTO( service ) );
        }

        return list;
    }
}
