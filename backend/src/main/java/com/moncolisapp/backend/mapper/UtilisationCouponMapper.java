package com.moncolisapp.backend.mapper;

import com.moncolisapp.backend.dto.UtilisationCouponDTO;
import com.moncolisapp.backend.entities.UtilisationCoupon;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {ClientMapper.class, CouponMapper.class})
public interface UtilisationCouponMapper {
    UtilisationCoupon toEntity(UtilisationCouponDTO utilisationCouponDTO);

    UtilisationCouponDTO toDto(UtilisationCoupon utilisationCoupon);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UtilisationCoupon partialUpdate(UtilisationCouponDTO utilisationCouponDTO, @MappingTarget UtilisationCoupon utilisationCoupon);
}