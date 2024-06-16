package com.moncolisapp.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tarifs")
public class Tarif implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private BigDecimal poidsMax;

    private BigDecimal prixFixe;

    private BigDecimal prixParKilo;

//    @Id
//    @ColumnDefault("nextval('tarifs_id_tarif_seq'")
//    @Column(name = "id_tarif", nullable = false)
//    public Integer getId() {
//        return id;
//    }

    @Column(name = "poids_max", nullable = false, precision = 10, scale = 2)
    public BigDecimal getPoidsMax() {
        return poidsMax;
    }

    @Column(name = "prix_fixe", nullable = false, precision = 10, scale = 2)
    public BigDecimal getPrixFixe() {
        return prixFixe;
    }

    @Column(name = "prix_par_kilo", nullable = false, precision = 10, scale = 2)
    public BigDecimal getPrixParKilo() {
        return prixParKilo;
    }

}