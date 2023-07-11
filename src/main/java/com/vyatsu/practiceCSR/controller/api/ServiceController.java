package com.vyatsu.practiceCSR.controller.api;

import com.vyatsu.practiceCSR.dto.api.ServiceDTO;
import com.vyatsu.practiceCSR.service.api.ServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServicesService servicesService;
    @GetMapping
    public ResponseEntity<List<ServiceDTO>> getAllService(){
        List<ServiceDTO> serviceDTOList = servicesService.getAllActiveServices();
        return ResponseEntity.ok(serviceDTOList);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable Long id) {
        return servicesService.softDeleteById(id);
    }

    @PostMapping
    public ResponseEntity<Void> createService(@RequestBody ServiceDTO serviceDTO){
        return servicesService.createService(serviceDTO);
    }
}
