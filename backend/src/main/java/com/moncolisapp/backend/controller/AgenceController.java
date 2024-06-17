package com.moncolisapp.backend.controller;

import com.moncolisapp.backend.service.AgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agences")
public class AgenceController {

    @Autowired
    private AgenceService agenceService;

    @GetMapping("/{ville}")
    public ResponseEntity<List<String>> getAgencesByVille(@PathVariable String ville) {
        return agenceService.getAgencesByVille(ville);
    }
}
