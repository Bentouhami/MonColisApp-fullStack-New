package com.moncolisapp.backend.mapper;

import com.moncolisapp.backend.dto.EnvoisDTO;
import com.moncolisapp.backend.entities.Envois;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {AgenceMapper.class, AgenceMapper.class, ClientMapper.class, CouponMapper.class, DestinataireMapper.class, TarifMapper.class, TransportMapper.class})
public interface EnvoisMapper {
    Envois toEntity(EnvoisDTO envoisDTO);

    EnvoisDTO toDto(Envois envois);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Envois partialUpdate(EnvoisDTO envoisDTO, @MappingTarget Envois envois);
}