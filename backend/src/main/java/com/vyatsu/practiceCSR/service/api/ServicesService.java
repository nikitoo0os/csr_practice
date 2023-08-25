package com.vyatsu.practiceCSR.service.api;

import com.vyatsu.practiceCSR.dto.api.ServiceDTO;
import com.vyatsu.practiceCSR.entity.api.Service;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ServicesService {
    List<ServiceDTO> getAllServices();
    List<ServiceDTO> getAllActiveServices();

    ServiceDTO getServiceDTOById(Long id);

    Service getServiceById(Long id);

    void updateService(Service service);

    ResponseEntity<Void> softDeleteById(String token, Long id);

    ResponseEntity<Void> createService(String token, ServiceDTO serviceDTO);

}
