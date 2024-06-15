package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Agence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenceRepository extends JpaRepository<Agence, Integer> {
}