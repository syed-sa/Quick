package com.justsearch.backend.service.ServiceBooking.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.justsearch.backend.constants.AppConstants;
import com.justsearch.backend.dto.BookingDetailsDto;
import com.justsearch.backend.model.BookingDetails;
import com.justsearch.backend.repository.BookingDetailsRepository;
import com.justsearch.backend.repository.NotificationRepository;
import com.justsearch.backend.repository.ServicesRepository;
import com.justsearch.backend.repository.UserRepository;
import com.justsearch.backend.service.Notification.NotificationService;
import com.justsearch.backend.service.ServiceBooking.BookService;

@Service
public class BookServiceImpl implements BookService {

    private BookingDetailsRepository _bookingDetailsRepository;
    private ServicesRepository _servicesRepository;
    private UserRepository _userRepository;
    private NotificationService _notificationService;

    public BookServiceImpl(BookingDetailsRepository bookingDetailsRepository, ServicesRepository servicesRepository,
            UserRepository userRepository,
            NotificationService notificationService) {
        _bookingDetailsRepository = bookingDetailsRepository;
        _servicesRepository = servicesRepository;
        _userRepository = userRepository;
        _notificationService = notificationService;
    }

    public void createBookingRequest(BookingDetailsDto bookserviceDto) {
        if (bookserviceDto == null) {
            throw new IllegalArgumentException("service must not be null");
        }
        _userRepository.findById(bookserviceDto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
        long serviceProviderId = _servicesRepository.findById(bookserviceDto.getServiceId()).get().userId;
        BookingDetails bookingDetails = new BookingDetails();
        System.out.println(bookserviceDto.getDescription());
        bookingDetails.setCustomerId(bookserviceDto.getCustomerId());
        bookingDetails.setServiceProviderId(serviceProviderId);
        bookingDetails.setServiceId(bookserviceDto.getServiceId());
        bookingDetails.setServiceName(bookserviceDto.getServiceName());
        bookingDetails.setBookingStatus(AppConstants.BOOKING_STATUS_PENDING);
        bookingDetails.setDescription(bookserviceDto.getDescription());
        bookingDetails.setCreatedAt(LocalDateTime.now());
        bookingDetails.setLocation(bookserviceDto.location);
        _bookingDetailsRepository.save(bookingDetails);
        _notificationService.createNotification(bookingDetails);
        System.out.println("Booking request created successfully for service: " + bookingDetails.getServiceName()
                + " with ID: " + bookingDetails.getId());

    }

    public List<BookingDetailsDto> getBookingRequests(long serviceProviderId) {
        var bookService = _bookingDetailsRepository.fetchBookingsWithCustomerInfo(serviceProviderId);
        return bookService;
    }

    public List<BookingDetailsDto> getMyBookings(long userId) {

        List<BookingDetailsDto> bookService = _bookingDetailsRepository.fetchBookingsWithServiceProviderInfo(userId);
        return bookService;
    }

    public void updateBooking(long bookingId, String status) {
        BookingDetails bookingDetails = _bookingDetailsRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID"));
        bookingDetails.setBookingStatus(status);
        if (status.equals(AppConstants.BOOKING_STATUS_CANCELLED)
                || status.equals(AppConstants.BOOKING_STATUS_REJECTED)) {
            _notificationService.createBookingRejectedNotification(bookingDetails);
            bookingDetails.setActive(false);
        }
        _bookingDetailsRepository.save(bookingDetails);
    }

}
