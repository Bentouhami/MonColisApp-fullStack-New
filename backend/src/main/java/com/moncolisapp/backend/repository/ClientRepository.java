package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

  boolean existsClientByEmail(String email);
}