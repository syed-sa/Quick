package com.justsearch.backend.mapper;
import com.justsearch.backend.dto.BookingDetailsDto;
import com.justsearch.backend.model.BookingDetails;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;



@Mapper(componentModel = "spring")
public interface BookingDetailsMapper {
    
    BookingDetailsMapper INSTANCE = Mappers.getMapper(BookingDetailsMapper.class);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "serviceProvider.id", target = "serviceProviderId")
    @Mapping(source = "serviceId", target = "serviceId")
    @Mapping(source = "serviceName", target = "serviceName")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "bookingStatus", target = "bookingStatus")
    @Mapping(source = "customer.phone", target = "phone")
    @Mapping(source = "customer.email", target = "email")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "location", target = "location")
    BookingDetailsDto toDto(BookingDetails bookingDetails);

    List<BookingDetailsDto> toDtoList(List<BookingDetails> bookingDetailsList);

    // If creating entity from DTO
    @Mapping(source = "customerId", target = "customer.id")
    @Mapping(source = "serviceProviderId", target = "serviceProvider.id")
    BookingDetails toEntity(BookingDetailsDto bookingDetailsDto);

    List<BookingDetails> toEntityList(List<BookingDetailsDto> bookingDetailsDtoList);
}
