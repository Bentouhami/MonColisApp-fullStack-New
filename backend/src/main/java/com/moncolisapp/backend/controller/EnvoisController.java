package com.moncolisapp.backend.controller;

import com.moncolisapp.backend.dto.EnvoisListDTO;
import com.moncolisapp.backend.dto.EnvoisRequestDTO;
import com.moncolisapp.backend.entities.Envois;
import com.moncolisapp.backend.entities.EnvoisListMapper;
import com.moncolisapp.backend.service.EnvoisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/envois")
public class EnvoisController {

    @Autowired
    private EnvoisService envoisService;
    @Autowired
    private EnvoisListMapper envoisListMapper;

    @PostMapping("/valider")
    public ResponseEntity<String> validerEnvoi(@RequestBody EnvoisRequestDTO envoisDTO, @AuthenticationPrincipal User user) {
        boolean success = envoisService.validerEnvoi(envoisDTO, user.getUsername());
        if (success) {
            return ResponseEntity.ok("Envoi validé avec succès");
        } else {
            return ResponseEntity.status(500).body("Erreur lors de la validation de l'envoi");
        }
    }

    @GetMapping("/fetch-envois")
    public ResponseEntity<List<EnvoisListDTO>> getUserEnvois(@AuthenticationPrincipal User userEmail) {
        String email = userEmail.getUsername();
        List<Envois> envois = envoisService.getEnvoisByClientEmail(email);
        if (envois.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<EnvoisListDTO> envoisListResponses = new ArrayList<>();
        for (Envois envoi : envois) {
            envoisListResponses.add(envoisListMapper.toDto(envoi));
        }
        return new ResponseEntity<>(envoisListResponses, HttpStatus.OK);
    }

    @PostMapping("/delete-envois")
    public ResponseEntity<String> deleteEnvois(@RequestBody List<Integer> envoisIds) {
        try {
            envoisService.deleteEnvoisByIds(envoisIds);
            return ResponseEntity.ok("Envois successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting envois: " + e.getMessage());
        }
    }

}
