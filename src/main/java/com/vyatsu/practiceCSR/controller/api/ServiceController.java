package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api.ServiceDTO;
import com.vyatsu.practiceCSR.entity.api.Service;
import com.vyatsu.practiceCSR.service.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServicesService servicesService;
    @GetMapping
    public ResponseEntity<List<ServiceDTO>> getAllService(){
        List<ServiceDTO> serviceDTOList = servicesService.getAllServices();
        return ResponseEntity.ok(serviceDTOList);
    }
}
