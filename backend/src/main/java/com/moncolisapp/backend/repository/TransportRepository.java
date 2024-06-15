package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportRepository extends JpaRepository<Transport, Integer> {
}