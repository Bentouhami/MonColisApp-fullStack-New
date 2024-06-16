package com.moncolisapp.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "clients", indexes = {
        @Index(name = "idx_clients_id_address", columnList = "id_address")
})
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String nom;

    private String prenom;

    private LocalDate dateDeNaissance;

    private Boolean sexe = false;

    private String telephone;

    private String email;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_address", nullable = false)
    private Address idAddress;

    private String motDePasse;

//    @Id
//    @ColumnDefault("nextval('clients_id_client_seq'")
//    @Column(name = "id_client", nullable = false)
//    public Integer getId() {
//        return id;
//    }

    @Column(name = "nom", nullable = false, length = 20)
    public String getNom() {
        return nom;
    }

    @Column(name = "prenom", nullable = false, length = 20)
    public String getPrenom() {
        return prenom;
    }

    @Column(name = "date_de_naissance", nullable = false)
    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    @Column(name = "sexe", nullable = false)
    public Boolean getSexe() {
        return sexe;
    }

    @Column(name = "telephone", nullable = false, length = 20)
    public String getTelephone() {
        return telephone;
    }

    @Column(name = "email", nullable = false, length = 150)
    public String getEmail() {
        return email;
    }


    @Column(name = "mot_de_passe", nullable = false, length = Integer.MAX_VALUE)
    public String getMotDePasse() {
        return motDePasse;
    }

}