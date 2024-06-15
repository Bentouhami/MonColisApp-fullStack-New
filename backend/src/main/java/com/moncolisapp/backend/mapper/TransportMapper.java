package com.moncolisapp.backend.mapper;

import com.moncolisapp.backend.dto.TransportDTO;
import com.moncolisapp.backend.entities.Transport;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TransportMapper {
    Transport toEntity(TransportDTO transportDTO);

    TransportDTO toDto(Transport transport);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Transport partialUpdate(TransportDTO transportDTO, @MappingTarget Transport transport);
}