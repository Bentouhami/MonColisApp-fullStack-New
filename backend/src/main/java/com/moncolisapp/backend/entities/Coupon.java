package com.moncolisapp.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "coupons", uniqueConstraints = {
        @UniqueConstraint(name = "coupons_code_coupon_key", columnNames = {"code_coupon"})
})
public class Coupon {
    private Integer id;

    private String codeCoupon;

    private String conditions;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private BigDecimal montantRemise;

    private Integer nombreUtilisationMax;

    private BigDecimal pourcentageRemise;

    @Id
    @ColumnDefault("nextval('coupons_id_coupon_seq'")
    @Column(name = "id_coupon", nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "code_coupon", nullable = false, length = 20)
    public String getCodeCoupon() {
        return codeCoupon;
    }

    @Column(name = "conditions", nullable = false)
    public String getConditions() {
        return conditions;
    }

    @Column(name = "date_debut", nullable = false)
    public LocalDate getDateDebut() {
        return dateDebut;
    }

    @Column(name = "date_fin", nullable = false)
    public LocalDate getDateFin() {
        return dateFin;
    }

    @Column(name = "montant_remise", nullable = false, precision = 10, scale = 2)
    public BigDecimal getMontantRemise() {
        return montantRemise;
    }

    @Column(name = "nombre_utilisation_max", nullable = false)
    public Integer getNombreUtilisationMax() {
        return nombreUtilisationMax;
    }

    @Column(name = "pourcentage_remise", nullable = false, precision = 5, scale = 2)
    public BigDecimal getPourcentageRemise() {
        return pourcentageRemise;
    }

}