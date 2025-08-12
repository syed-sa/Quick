package com.justsearch.backend.mapper;

import com.justsearch.backend.dto.ServiceDto;
import com.justsearch.backend.model.Services;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    ServiceDto toDto(Services service);

    List<ServiceDto> toDtoList(List<Services> services);
    Services toEntity(ServiceDto serviceDto);
    List<Services> toEntityList(List<ServiceDto> serviceDtos);
}
