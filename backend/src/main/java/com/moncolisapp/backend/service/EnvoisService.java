package com.moncolisapp.backend.service;

import com.moncolisapp.backend.repository.ColisRepository;
import com.moncolisapp.backend.repository.EnvoisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnvoisService {

    @Autowired
    private EnvoisRepository envoisRepository;

    @Autowired
    private ColisRepository colisRepository;


}
