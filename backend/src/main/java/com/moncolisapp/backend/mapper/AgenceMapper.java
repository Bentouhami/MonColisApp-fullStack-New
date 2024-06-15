package com.moncolisapp.backend.mapper;

import com.moncolisapp.backend.dto.AgenceDTO;
import com.moncolisapp.backend.entities.Agence;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {AddressMapper.class})
public interface AgenceMapper {
    Agence toEntity(AgenceDTO agenceDTO);

    AgenceDTO toDto(Agence agence);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Agence partialUpdate(AgenceDTO agenceDTO, @MappingTarget Agence agence);
}