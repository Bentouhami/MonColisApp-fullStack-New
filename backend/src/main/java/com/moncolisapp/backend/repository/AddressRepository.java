package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

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

}