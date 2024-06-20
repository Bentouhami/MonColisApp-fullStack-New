package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Envois;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnvoisRepository extends JpaRepository<Envois, Integer> {

    //    @Query("SELECT e FROM Envois e WHERE e.idClient = :idClient")
    List<Envois> findByIdClient_Id(Integer idClient);
}