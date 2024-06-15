package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Colis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColisRepository extends JpaRepository<Colis, Integer> {
}