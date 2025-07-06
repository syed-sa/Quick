package com.justsearch.backend.service.ServiceBooking.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.justsearch.backend.constants.AppConstants;
import com.justsearch.backend.dto.BookServiceDto;
import com.justsearch.backend.dto.mapper.BookingDetailsMapper;
import com.justsearch.backend.model.BookingDetails;
import com.justsearch.backend.repository.BookingDetailsRepository;
import com.justsearch.backend.repository.ServicesRepository;
import com.justsearch.backend.service.ServiceBooking.BookService;

@Service
public class BookServiceImpl implements BookService {

    private BookingDetailsRepository _bookingDetailsRepository;
    private ServicesRepository _servicesRepository;
    private BookingDetailsMapper _bookingDetailsMapper;

    public BookServiceImpl(BookingDetailsRepository bookingDetailsRepository, ServicesRepository servicesRepository,
            BookingDetailsMapper bookingDetailsMapper) {
        _bookingDetailsRepository = bookingDetailsRepository;
        _servicesRepository = servicesRepository;
        _bookingDetailsMapper = bookingDetailsMapper;
    }

    public void createBookingRequest(BookServiceDto bookserviceDto) {
        if (bookserviceDto == null) {
            throw new IllegalArgumentException("service must not be null");
        }
        long serviceProviderId = _servicesRepository.findById(bookserviceDto.getServiceId()).get().userId;
        BookingDetails bookingDetails = new BookingDetails();
        System.out.println(bookserviceDto.getDescription());
        bookingDetails.customerId = bookserviceDto.getCustomerId();
        bookingDetails.serviceProviderId = serviceProviderId;
        bookingDetails.serviceId = bookserviceDto.getServiceId();
        bookingDetails.serviceName = bookserviceDto.getServiceName();
        bookingDetails.bookingDate = bookserviceDto.getBookingDate();
        bookingDetails.timeSlot = bookserviceDto.getTimeSlot();
        bookingDetails.bookingStatus = AppConstants.BOOKING_STATUS_PENDING;
        bookingDetails.description = bookserviceDto.getDescription();
        bookingDetails.createdAt = LocalDateTime.now();
        _bookingDetailsRepository.save(bookingDetails);

    }

    public List<BookServiceDto> getBookingRequests(long userId) {
        List<BookingDetails> bookingDetails = _bookingDetailsRepository.findAllByServiceProviderId(userId);
        List<BookServiceDto> bookService = _bookingDetailsMapper.toDtoList(
                bookingDetails);
        return bookService;
    }

}
