package com.vyatsu.practiceCSR.service.api.impl;

import com.vyatsu.practiceCSR.config.auth.UserAuthenticationProvider;
import com.vyatsu.practiceCSR.dto.api.ServiceDTO;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.logger.LoggerCSR;
import com.vyatsu.practiceCSR.logger.enumDebugLog;
import com.vyatsu.practiceCSR.mapper.ServiceMapper;
import com.vyatsu.practiceCSR.repository.ServiceRepository;
import com.vyatsu.practiceCSR.service.api.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ServicesServiceImpl implements ServicesService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final UserAuthenticationProvider authenticationProvider;

    @Override
    public List<ServiceDTO> getAllServices() {
        List<com.vyatsu.practiceCSR.entity.api.Service> serviceDTOList = serviceRepository.findAll();
        return serviceMapper.listServiceToListServiceDTO(serviceDTOList);
    }

    @Override
    public List<ServiceDTO> getAllActiveServices() {
        List<com.vyatsu.practiceCSR.entity.api.Service> serviceDTOList = serviceRepository.findAllActive();
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
    public ResponseEntity<Void> softDeleteById(String token, Long id) {
        String jwtToken = token.substring(7);
        Authentication authentication = authenticationProvider.validateToken(jwtToken);
        Long userId = ((UserAuthDto) authentication.getPrincipal()).getId();

        com.vyatsu.practiceCSR.entity.api.Service service = getServiceById(id);
        if (service == null) {
            return ResponseEntity.notFound().build();
        }
        service.setIsActive(false);
        updateService(service);
        LoggerCSR.createDebugMsg(enumDebugLog.DROP_SERVICE, userId, id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity createService(String token, ServiceDTO serviceDTO) {
        String jwtToken = token.substring(7);
        Authentication authentication = authenticationProvider.validateToken(jwtToken);
        Long userId = ((UserAuthDto) authentication.getPrincipal()).getId();

        if (serviceRepository.existsByName(serviceDTO.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Услуга с таким именем уже существует");
        }

        com.vyatsu.practiceCSR.entity.api.Service service = new com.vyatsu.practiceCSR.entity.api.Service();
        service.setName(serviceDTO.getName());
        service.setIsActive(true);
        service = serviceRepository.save(service);

        LoggerCSR.createDebugMsg(enumDebugLog.CREATE_SERVICE, userId, Long.valueOf(service.getId()));
        return ResponseEntity.ok("Услуга успешно добавлена");
    }
}
