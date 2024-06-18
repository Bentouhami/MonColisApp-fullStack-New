package com.moncolisapp.backend.controller;

import com.moncolisapp.backend.dto.SimulationResponse;
import com.moncolisapp.backend.service.EnvoisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/envois")
public class EnvoisController {

    @Autowired
    private EnvoisService envoisService;

    @PostMapping("/valider")
    public ResponseEntity<String> validerEnvoi(@RequestBody SimulationResponse simulationData) {
//        boolean success = envoisService.validerEnvoi(simulationData);
//        if (success) {
//            return ResponseEntity.ok("Envoi validé avec succès");
//        } else {
//            return ResponseEntity.status(500).body("Erreur lors de la validation de l'envoi");
//        }
        return ResponseEntity.ok("Envoi validé avec succès");
    }
}
