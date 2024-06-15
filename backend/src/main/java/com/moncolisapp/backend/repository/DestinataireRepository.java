package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Destinataire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinataireRepository extends JpaRepository<Destinataire, Integer> {
}