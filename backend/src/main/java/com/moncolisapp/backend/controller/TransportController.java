package com.moncolisapp.backend.controller;

import com.moncolisapp.backend.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/transports")
public class TransportController {

    @Autowired
    private TransportService transportService;

    @PostMapping("/verify-space")
    public ResponseEntity<Map<String, Object>> verifySpace(@RequestBody Map<String, BigDecimal> request) {
        BigDecimal poidsTotal = request.get("poidsTotal");
        BigDecimal volumeTotal = request.get("volumeTotal");

        boolean isAvailable = transportService.checkTransportAvailability(poidsTotal, volumeTotal);

        Map<String, Object> response = new HashMap<>();
        response.put("available", isAvailable);

        if (isAvailable) {
            response.put("nextAvailableDate", transportService.calculateNextAvailableDate());
        }
        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
