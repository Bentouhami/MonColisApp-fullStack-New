package com.moncolisapp.backend.controller;

import com.moncolisapp.backend.service.AgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/agences")
public class AgencesControlle {

    @Autowired
    private AgenceService agenceService;

    @RequestMapping("/{ville")
    public ResponseEntity<String> getAgencesByVille(@PathVariable String ville) {
        return agenceService.getAgencesByVille(ville);
    }
}
