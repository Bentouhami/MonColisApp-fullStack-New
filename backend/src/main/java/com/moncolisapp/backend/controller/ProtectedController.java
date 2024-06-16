package com.moncolisapp.backend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/protected")
public class ProtectedController {

    @GetMapping("/resource")
    public String getProtectedResource() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Access to protected resource granted! Welcome " + authentication.getName();
    }
}
