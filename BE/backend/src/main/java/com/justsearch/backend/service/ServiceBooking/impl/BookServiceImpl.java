package com.justsearch.backend.service.ServiceBooking.impl;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.justsearch.backend.constants.AppConstants;
import com.justsearch.backend.dto.BookServiceDto;
import com.justsearch.backend.model.BookingDetails;
import com.justsearch.backend.repository.BookingDetailsRepository;
import com.justsearch.backend.repository.ServicesRepository;
import com.justsearch.backend.service.ServiceBooking.BookService;
@Service
public class BookServiceImpl implements BookService {

    private BookingDetailsRepository _bookingDetailsRepository;
    private ServicesRepository _servicesRepository;

    public BookServiceImpl(BookingDetailsRepository bookingDetailsRepository, ServicesRepository servicesRepository) {
        _bookingDetailsRepository = bookingDetailsRepository;
        _servicesRepository = servicesRepository;
    }

    public void createBookingRequest(BookServiceDto bookserviceDto) {
          if (bookserviceDto == null) {
            throw new IllegalArgumentException("service must not be null");
        }
        long serviceProviderId = _servicesRepository.findById(bookserviceDto.getServiceId()).get().userId;
        BookingDetails bookingDetails = new BookingDetails();

        bookingDetails.customerId = bookserviceDto.getUserId();
        bookingDetails.serviceproviderId = serviceProviderId;
        bookingDetails.Date = LocalDate.now();
        bookingDetails.timeSlot = bookserviceDto.getTimeSlot();
        bookingDetails.bookingstatus = AppConstants.BOOKING_STATUS_PENDING;
        _bookingDetailsRepository.save(bookingDetails);

    }
}
