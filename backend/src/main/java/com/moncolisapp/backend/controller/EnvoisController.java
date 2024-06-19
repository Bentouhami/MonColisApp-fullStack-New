package com.moncolisapp.backend.controller;

import com.moncolisapp.backend.dto.EnvoisRequestDTO;
import com.moncolisapp.backend.service.EnvoisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
    public ResponseEntity<String> validerEnvoi(@RequestBody EnvoisRequestDTO envoisDTO, @AuthenticationPrincipal User user) {
        // Log the received DTO
        System.out.println("User: " + user);
        System.out.println(envoisDTO);


        boolean success = envoisService.validerEnvoi(envoisDTO, user.getUsername());
        if (success) {
            return ResponseEntity.ok("Envoi validé avec succès");
        } else {
            return ResponseEntity.status(500).body("Erreur lors de la validation de l'envoi");
        }
    }
}
