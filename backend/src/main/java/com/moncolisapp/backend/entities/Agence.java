package com.moncolisapp.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "agences", indexes = {
        @Index(name = "idx_agences_id_address", columnList = "id_address")
})
public class Agence {
    private Integer id;

    private Address idAddress;

    private String nomAgence;

    private String localisation;

    @Id
    @ColumnDefault("nextval('agences_id_agence_seq'")
    @Column(name = "id_agence", nullable = false)
    public Integer getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_address", nullable = false)
    public Address getIdAddress() {
        return idAddress;
    }

    @Column(name = "nom_agence", nullable = false, length = 50)
    public String getNomAgence() {
        return nomAgence;
    }

    @Column(name = "localisation")
    public String getLocalisation() {
        return localisation;
    }

}