package com.moncolisapp.backend.entities;

import com.moncolisapp.backend.dto.EnvoisListDTO;
import com.moncolisapp.backend.mapper.AgenceMapper;
import com.moncolisapp.backend.mapper.ClientMapper;
import com.moncolisapp.backend.mapper.CouponMapper;
import com.moncolisapp.backend.mapper.DestinataireMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {AgenceMapper.class, AgenceMapper.class, ClientMapper.class, CouponMapper.class, DestinataireMapper.class})
public interface EnvoisListMapper {
    Envois toEntity(EnvoisListDTO envoisListDTO);

    EnvoisListDTO toDto(Envois envois);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Envois partialUpdate(EnvoisListDTO envoisListDTO, @MappingTarget Envois envois);
}