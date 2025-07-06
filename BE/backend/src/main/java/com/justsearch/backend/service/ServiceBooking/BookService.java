package com.justsearch.backend.service.ServiceBooking;

import java.util.List;

import com.justsearch.backend.dto.BookServiceDto;

public interface BookService {

     void createBookingRequest(BookServiceDto bookserviceDto);
     List<BookServiceDto> getBookingRequests(long userId);
} 