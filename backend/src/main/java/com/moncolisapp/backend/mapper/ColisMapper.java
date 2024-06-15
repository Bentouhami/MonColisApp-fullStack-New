package com.moncolisapp.backend.mapper;

import com.moncolisapp.backend.dto.ColisDTO;
import com.moncolisapp.backend.entities.Colis;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {EnvoisMapper.class})
public interface ColisMapper {
    Colis toEntity(ColisDTO colisDTO);

    ColisDTO toDto(Colis colis);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Colis partialUpdate(ColisDTO colisDTO, @MappingTarget Colis colis);
}