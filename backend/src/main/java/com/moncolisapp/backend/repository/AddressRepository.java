package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findAddressByRueAndNumeroAndVilleAndCodepostalAndPays(
            String rue,
            String numero,
            String ville,
            String codePostal,
            String pays
    );

    boolean existsAddressByRueAndNumeroAndVilleAndCodepostalAndPays(
            String rue,
            String numero,
            String ville,
            String codePostal,
            String pays
    );

    // Select all countries where we have cities with existing agencies
    @Query("SELECT DISTINCT a.pays FROM Address a JOIN Agence ag ON a.id = ag.idAddress.id")
    List<String> findAllPaysWithAgences();

    // Select all cities where we have agencies for a specific country
    @Query("SELECT DISTINCT a.ville FROM Address a JOIN Agence ag ON a.id = ag.idAddress.id WHERE a.pays = :pays")
    List<String> findCitiesWithAgencesByCountry(@Param("pays") String pays);
}
