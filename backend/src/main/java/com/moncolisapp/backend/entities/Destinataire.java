package com.moncolisapp.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "destinataires", indexes = {
        @Index(name = "idx_destinataires_id_address", columnList = "id_address")
})
public class Destinataire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String nomPrenom;

    private String telephone;

    private String email;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_address", nullable = false)
    private Address idAddress;

//    @Id
//    @ColumnDefault("nextval('destinataires_id_destinataire_seq'")
//    @Column(name = "id_destinataire", nullable = false)
//    public Integer getId() {
//        return id;
//    }

    @Column(name = "nom_prenom", nullable = false, length = 150)
    public String getNomPrenom() {
        return nomPrenom;
    }

    @Column(name = "telephone", length = 20)
    public String getTelephone() {
        return telephone;
    }

    @Column(name = "email", length = 150)
    public String getEmail() {
        return email;
    }

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "id_address", nullable = false)
    public Address getIdAddress() {
        return idAddress;
    }

}