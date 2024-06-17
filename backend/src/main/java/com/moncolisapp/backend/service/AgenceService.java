package com.moncolisapp.backend.service;

import com.moncolisapp.backend.repository.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgenceService {

    @Autowired
    private AgenceRepository agenceRepository;

    public ResponseEntity<List<String>> getAgencesByVille(String ville) {
        List<String> agenceList = agenceRepository.findAgencesByVille(ville);
        return ResponseEntity.ok(agenceList);
    }



}
