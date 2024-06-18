package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Tarif;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface TarifRepository extends JpaRepository<Tarif, Integer> {
    Optional<Tarif> findByPoidsMaxAndPrixFixeIsNotNull(BigDecimal poidsMax);
    Optional<Tarif> findByPoidsMaxIsNullAndPrixParKiloIsNotNull();
}
