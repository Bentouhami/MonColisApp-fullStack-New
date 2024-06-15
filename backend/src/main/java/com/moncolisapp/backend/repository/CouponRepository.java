package com.moncolisapp.backend.repository;

import com.moncolisapp.backend.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
}