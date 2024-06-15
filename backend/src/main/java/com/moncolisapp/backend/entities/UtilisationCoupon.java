package com.moncolisapp.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "utilisation_coupons")
public class UtilisationCoupon {
    private UtilisationCouponId id;

    private Client idClient;

    private Coupon idCoupon;

    @EmbeddedId
    public UtilisationCouponId getId() {
        return id;
    }

    @MapsId("idClient")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_client", nullable = false)
    public Client getIdClient() {
        return idClient;
    }

    @MapsId("idCoupon")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_coupon", nullable = false)
    public Coupon getIdCoupon() {
        return idCoupon;
    }

}