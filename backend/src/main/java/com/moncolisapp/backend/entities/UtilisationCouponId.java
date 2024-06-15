package com.moncolisapp.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class UtilisationCouponId implements Serializable {
    private static final long serialVersionUID = -2550995604867793644L;
    private Integer idClient;

    private Integer idCoupon;

    @Column(name = "id_client", nullable = false)
    public Integer getIdClient() {
        return idClient;
    }

    @Column(name = "id_coupon", nullable = false)
    public Integer getIdCoupon() {
        return idCoupon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UtilisationCouponId entity = (UtilisationCouponId) o;
        return Objects.equals(this.idCoupon, entity.idCoupon) &&
                Objects.equals(this.idClient, entity.idClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCoupon, idClient);
    }

}