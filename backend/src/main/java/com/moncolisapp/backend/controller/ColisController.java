package com.moncolisapp.backend.controller;

import com.moncolisapp.backend.dto.ColisDTO;
import com.moncolisapp.backend.service.ColisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/colis")
public class ColisController {

    @Autowired
    private ColisService colisService;

    @PostMapping("/validate")
    public ResponseEntity<List<ColisDTO>> validateColis(@RequestBody List<ColisDTO> colisDTOList) {
        List<ColisDTO> validatedColis = colisService.validateColis(colisDTOList);
        return ResponseEntity.ok(validatedColis);
    }
}
