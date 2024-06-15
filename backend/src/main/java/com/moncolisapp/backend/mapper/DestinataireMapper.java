package com.moncolisapp.backend.mapper;

import com.moncolisapp.backend.dto.DestinataireDTO;
import com.moncolisapp.backend.entities.Destinataire;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {AddressMapper.class})
public interface DestinataireMapper {
    Destinataire toEntity(DestinataireDTO destinataireDTO);

    DestinataireDTO toDto(Destinataire destinataire);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Destinataire partialUpdate(DestinataireDTO destinataireDTO, @MappingTarget Destinataire destinataire);
}