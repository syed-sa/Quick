package com.justsearch.backend.service.ServiceBooking;
import java.util.List;
import com.justsearch.backend.dto.BookingDetailsDto;
public interface BookService {

     void createBookingRequest(BookingDetailsDto bookserviceDto);
     List<BookingDetailsDto> getBookingRequests(long userId);
     List<BookingDetailsDto> getMyBookings(long userId);
     void updateBooking(long bookingId, String status);
} 