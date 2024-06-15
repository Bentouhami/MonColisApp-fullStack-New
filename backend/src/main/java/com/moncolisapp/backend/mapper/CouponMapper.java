package com.moncolisapp.backend.mapper;

import com.moncolisapp.backend.dto.CouponDTO;
import com.moncolisapp.backend.entities.Coupon;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CouponMapper {
    Coupon toEntity(CouponDTO couponDTO);

    CouponDTO toDto(Coupon coupon);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Coupon partialUpdate(CouponDTO couponDTO, @MappingTarget Coupon coupon);
}