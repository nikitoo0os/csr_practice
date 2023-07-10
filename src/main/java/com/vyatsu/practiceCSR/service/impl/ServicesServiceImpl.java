package com.vyatsu.practiceCSR.service.impl;

import com.vyatsu.practiceCSR.dto.api.ServiceDTO;
import com.vyatsu.practiceCSR.mapper.ServiceMapper;
import com.vyatsu.practiceCSR.repository.ServiceRepository;
import com.vyatsu.practiceCSR.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Override
    public ServiceDTO getServiceDTOById(Long id) {
        com.vyatsu.practiceCSR.entity.api.Service service = serviceRepository.findById(id).get();
        return serviceMapper.serviceToServiceDTO(service);
    }

    @Override
    public com.vyatsu.practiceCSR.entity.api.Service getServiceById(Long id) {
        return serviceRepository.findById(id).get();
    }

    @Override
    public void updateService(com.vyatsu.practiceCSR.entity.api.Service service) {
        serviceRepository.save(service);
    }

    @Override
    public ResponseEntity<Void> softDeleteById(Long id) {
        com.vyatsu.practiceCSR.entity.api.Service service = getServiceById(id);
        if (service == null) {
            return ResponseEntity.notFound().build();
        }
        service.setIsActive(false);
        updateService(service);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity createService(String name) {
        if (serviceRepository.existsByName(name)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Услуга с таким именем уже существует");
        }

        com.vyatsu.practiceCSR.entity.api.Service service = new com.vyatsu.practiceCSR.entity.api.Service();
        service.setName(name);
        service.setIsActive(true);
        serviceRepository.save(service);

        return ResponseEntity.ok("Услуга успешно добавлена");
    }
}
