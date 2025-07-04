package com.justsearch.backend.dto.mapper;
import java.util.List;
import org.mapstruct.Mapper;
import com.justsearch.backend.dto.ServiceDto;
import com.justsearch.backend.model.Services;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    ServiceDto toDto(Services services);
    List<ServiceDto> toDtoList(List<Services> services);
    Services toEntity(ServiceDto serviceDto);
}
