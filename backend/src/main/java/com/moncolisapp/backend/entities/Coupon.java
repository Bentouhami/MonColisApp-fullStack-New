package com.moncolisapp.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
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
public class Coupon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(name = "code_coupon", nullable = false, length = 20)
    private String codeCoupon;

    private String conditions;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    private BigDecimal montantRemise;

    private Integer nombreUtilisationMax;

    private BigDecimal pourcentageRemise;



}