package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AgenceRepository extends JpaRepository<Agence, Integer> {


    @Query("SELECT DISTINCT a.nomAgence" +
            " FROM Agence a" +
            " JOIN Address addr" +
            " ON a.idAddress.id = addr.id" +
            " WHERE addr.ville = :ville")
    List<String> findAgencesByVille(String ville);

}