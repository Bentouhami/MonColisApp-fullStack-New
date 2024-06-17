package com.moncolisapp.backend.service;

import com.moncolisapp.backend.repository.AgenceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AgenceService {

    private AgenceRepository agenceRepository;

    public ResponseEntity<String> getAgencesByVille(String ville) {
        return null;
    }


}
