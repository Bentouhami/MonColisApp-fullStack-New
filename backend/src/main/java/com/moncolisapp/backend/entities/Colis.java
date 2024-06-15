package com.moncolisapp.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "colis")
public class Colis {
    private Integer id;

    private Envois idEnvoi;

    private BigDecimal hauteur;

    private BigDecimal largeur;

    private BigDecimal longueur;

    private BigDecimal poidsColis;

    private BigDecimal volumeColis;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('colis_id_colis_seq'")
    @Column(name = "id_colis", nullable = false)
    public Integer getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_envoi", nullable = false)
    public Envois getIdEnvoi() {
        return idEnvoi;
    }

    @Column(name = "hauteur", nullable = false, precision = 10, scale = 2)
    public BigDecimal getHauteur() {
        return hauteur;
    }

    @Column(name = "largeur", nullable = false, precision = 10, scale = 2)
    public BigDecimal getLargeur() {
        return largeur;
    }

    @Column(name = "longueur", nullable = false, precision = 10, scale = 2)
    public BigDecimal getLongueur() {
        return longueur;
    }

    @Column(name = "poids_colis", nullable = false, precision = 10, scale = 2)
    public BigDecimal getPoidsColis() {
        return poidsColis;
    }

    @Column(name = "volume_colis", nullable = false, precision = 10, scale = 2)
    public BigDecimal getVolumeColis() {
        return volumeColis;
    }

}