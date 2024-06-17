package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

//    List<Address> findByPays(String pays);

    @Query("SELECT DISTINCT a.pays FROM Address a")
    List<String> findAllPays();

    @Query("SELECT DISTINCT a.ville FROM Address a WHERE a.pays = :pays")
    List<String> findDistinctByVille(String pays);


//    Address findAddressById(Integer addresseId);
}