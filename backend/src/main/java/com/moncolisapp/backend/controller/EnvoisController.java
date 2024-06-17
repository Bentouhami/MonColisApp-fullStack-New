package com.moncolisapp.backend.controller;

import com.moncolisapp.backend.service.EnvoisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/envois")
public class EnvoisController {

    @Autowired
    private EnvoisService envoisService;
}
