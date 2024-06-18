package com.moncolisapp.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "agences", indexes = {
        @Index(name = "idx_agences_id_address", columnList = "id_address")
})
public class Agence implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_address", nullable = false)
    private Address idAddress;

    private String nomAgence;

    private String localisation;


    @Column(name = "nom_agence", nullable = false, length = 50)
    public String getNomAgence() {
        return nomAgence;
    }

    @Column(name = "localisation")
    public String getLocalisation() {
        return localisation;
    }

}