package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

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

    List<Address> findByPays(String pays);


//    Address findAddressById(Integer addresseId);
}