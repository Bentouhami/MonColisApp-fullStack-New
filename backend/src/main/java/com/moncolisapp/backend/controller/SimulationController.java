package com.moncolisapp.backend.controller;

import com.moncolisapp.backend.dto.SimulationRequest;
import com.moncolisapp.backend.dto.SimulationResponse;
import com.moncolisapp.backend.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/simulation")
public class SimulationController {

    @Autowired
    private SimulationService simulationService;

    @PostMapping("/calculate")
    public ResponseEntity<SimulationResponse> calculateSimulation(@RequestBody SimulationRequest request) {
        SimulationResponse response = simulationService.calculateSimulation(request);
        return ResponseEntity.ok(response);
    }
}
