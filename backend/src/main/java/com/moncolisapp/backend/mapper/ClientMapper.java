package com.moncolisapp.backend.mapper;

import com.moncolisapp.backend.dto.ClientDTO;
import com.moncolisapp.backend.entities.Client;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {AddressMapper.class})
public interface ClientMapper {
    Client toEntity(ClientDTO clientDTO);

    ClientDTO toDto(Client client);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Client partialUpdate(ClientDTO clientDTO, @MappingTarget Client client);
}