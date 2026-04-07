package com.leets.assignment.test.service;

import com.leets.assignment.test.dto.HealthCheckResponseDto;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {

    public HealthCheckResponseDto checkStatus() {
        return new HealthCheckResponseDto("OK");
    }
}
