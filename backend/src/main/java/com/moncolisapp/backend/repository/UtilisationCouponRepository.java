package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.UtilisationCoupon;
import com.moncolisapp.backend.entities.UtilisationCouponId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisationCouponRepository extends JpaRepository<UtilisationCoupon, UtilisationCouponId> {
}