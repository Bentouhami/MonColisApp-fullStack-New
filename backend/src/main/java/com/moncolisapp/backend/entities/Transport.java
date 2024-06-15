package com.moncolisapp.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transports")
public class Transport {
    private Integer id;

    private Boolean estDisponible = false;

    private String plaqueMatriculation;

    private BigDecimal poidsActuel;

    private BigDecimal poidsDeBase;

    private BigDecimal volumeActuel;

    private BigDecimal volumeDeBase;

    @Id
    @ColumnDefault("nextval('transports_id_transport_seq'")
    @Column(name = "id_transport", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "est_disponible", nullable = false)
    public Boolean getEstDisponible() {
        return estDisponible;
    }

    @Column(name = "plaque_matriculation", nullable = false, length = 15)
    public String getPlaqueMatriculation() {
        return plaqueMatriculation;
    }

    @Column(name = "poids_actuel", nullable = false, precision = 10, scale = 2)
    public BigDecimal getPoidsActuel() {
        return poidsActuel;
    }

    @ColumnDefault("15000.00")
    @Column(name = "poids_de_base", nullable = false, precision = 10, scale = 2)
    public BigDecimal getPoidsDeBase() {
        return poidsDeBase;
    }

    @Column(name = "volume_actuel", nullable = false, precision = 10, scale = 2)
    public BigDecimal getVolumeActuel() {
        return volumeActuel;
    }

    @ColumnDefault("42000000.00")
    @Column(name = "volume_de_base", nullable = false, precision = 10, scale = 2)
    public BigDecimal getVolumeDeBase() {
        return volumeDeBase;
    }

}