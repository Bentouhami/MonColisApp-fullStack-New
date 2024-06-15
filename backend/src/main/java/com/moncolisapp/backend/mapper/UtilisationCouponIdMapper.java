package com.moncolisapp.backend.mapper;

import com.moncolisapp.backend.dto.UtilisationCouponIdDTO;
import com.moncolisapp.backend.entities.UtilisationCouponId;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UtilisationCouponIdMapper {
    UtilisationCouponId toEntity(UtilisationCouponIdDTO utilisationCouponIdDTO);

    UtilisationCouponIdDTO toDto(UtilisationCouponId utilisationCouponId);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UtilisationCouponId partialUpdate(UtilisationCouponIdDTO utilisationCouponIdDTO, @MappingTarget UtilisationCouponId utilisationCouponId);
}