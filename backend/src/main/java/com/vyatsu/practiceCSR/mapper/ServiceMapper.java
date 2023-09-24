package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.ServiceDTO;
import com.vyatsu.practiceCSR.entity.api.Service;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDTO serviceToServiceDTO(Service service);
    List<ServiceDTO> listServiceToListServiceDTO(List<Service> serviceList);
}
