package com.moncolisapp.backend.mapper;

import com.moncolisapp.backend.dto.TarifDTO;
import com.moncolisapp.backend.entities.Tarif;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TarifMapper {
    Tarif toEntity(TarifDTO tarifDTO);

    TarifDTO toDto(Tarif tarif);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tarif partialUpdate(TarifDTO tarifDTO, @MappingTarget Tarif tarif);
}