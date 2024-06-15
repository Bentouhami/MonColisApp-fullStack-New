package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Tarif;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarifRepository extends JpaRepository<Tarif, Integer> {
}