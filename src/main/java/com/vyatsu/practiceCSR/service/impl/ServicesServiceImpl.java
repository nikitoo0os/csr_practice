package com.vyatsu.practiceCSR.service.impl;

import com.vyatsu.practiceCSR.dto.api.ServiceDTO;
import com.vyatsu.practiceCSR.mapper.ServiceMapper;
import com.vyatsu.practiceCSR.repository.ServiceRepository;
import com.vyatsu.practiceCSR.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ServicesServiceImpl implements ServicesService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public List<ServiceDTO> getAllServices() {
        List<com.vyatsu.practiceCSR.entity.api.Service> serviceDTOList = serviceRepository.findAll();
        return serviceMapper.listServiceToListServiceDTO(serviceDTOList);
    }
}
