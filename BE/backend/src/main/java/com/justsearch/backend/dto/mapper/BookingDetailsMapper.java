package com.justsearch.backend.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.justsearch.backend.dto.BookServiceDto;
import com.justsearch.backend.model.BookingDetails;
@Mapper(componentModel = "spring")
public interface BookingDetailsMapper {

    BookingDetails toEntity(BookServiceDto dto);
    List<BookServiceDto> toDtoList(List<BookingDetails> entities);
    BookServiceDto toDto(BookingDetails entity);
}