package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Envois;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvoisRepository extends JpaRepository<Envois, Integer> {
}