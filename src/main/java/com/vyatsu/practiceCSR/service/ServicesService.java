package com.vyatsu.practiceCSR.service;

import com.vyatsu.practiceCSR.dto.api.ServiceDTO;
import com.vyatsu.practiceCSR.entity.api.Service;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServicesService {
    List<ServiceDTO> getAllServices();

    ServiceDTO getServiceDTOById(Long id);

    Service getServiceById(Long id);

    void updateService(Service service);

    ResponseEntity<Void> softDeleteById(Long id);

    ResponseEntity<Void> createService(String name);

}
