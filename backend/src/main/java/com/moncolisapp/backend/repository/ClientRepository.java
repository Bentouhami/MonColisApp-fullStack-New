package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    //  @Query("SELECT c FROM Client c JOIN c.idAddress a WHERE c.email = :email")
    boolean existsClientByEmail(String email);

    Client findClientByEmail(String email);

    @Query("SELECT a.id FROM Client c JOIN c.idAddress a WHERE c.id = :id")
    Integer findAddressIdByClientID(Integer id);
}