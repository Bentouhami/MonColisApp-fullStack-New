package com.moncolisapp.backend.controller;

import com.moncolisapp.backend.dto.ColisDTO;
import com.moncolisapp.backend.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/simulations")
public class SimulationController {

    @Autowired
    private SimulationService simulationService;

    @PostMapping("/simulate")
    public ResponseEntity<?> simulateShipment(@RequestBody List<ColisDTO> colisList) {
        try {
            return ResponseEntity.ok(simulationService.simulateShipment(colisList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
