package com.moncolisapp.backend.mapper;

import com.moncolisapp.backend.dto.AddressDTO;
import com.moncolisapp.backend.entities.Address;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressDTO addressDTO);

    AddressDTO toDto(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address partialUpdate(AddressDTO addressDTO, @MappingTarget Address address);
}