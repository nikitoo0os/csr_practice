package com.vyatsu.practiceCSR.service;

import com.vyatsu.practiceCSR.dto.api.ServiceDTO;
import com.vyatsu.practiceCSR.entity.api.Service;

import java.util.List;

public interface ServicesService {
    public List<ServiceDTO> getAllServices();
}
