package com.justsearch.backend.service.ServiceBooking.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.justsearch.backend.constants.AppConstants;
import com.justsearch.backend.dto.BookingDetailsDto;
import com.justsearch.backend.mapper.BookingDetailsMapper;
import com.justsearch.backend.model.BookingDetails;
import com.justsearch.backend.model.Services;
import com.justsearch.backend.model.User;
import com.justsearch.backend.repository.BookingDetailsRepository;
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
    private BookingDetailsMapper _bookingDetailsMapper;

    public BookServiceImpl(BookingDetailsRepository bookingDetailsRepository, ServicesRepository servicesRepository,
            UserRepository userRepository,
            NotificationService notificationService, BookingDetailsMapper bookingDetailsMapper) {
        _bookingDetailsRepository = bookingDetailsRepository;
        _servicesRepository = servicesRepository;
        _userRepository = userRepository;
        _notificationService = notificationService;
        _bookingDetailsMapper = bookingDetailsMapper;
    }

    public void createBookingRequest(BookingDetailsDto bookserviceDto) {
        if (bookserviceDto == null) {
            throw new IllegalArgumentException("service must not be null");
        }

        User customer = _userRepository.findById(bookserviceDto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

        Services service = _servicesRepository.findById(bookserviceDto.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid service ID"));


        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setCustomer(customer);
        bookingDetails.setService(service);
        bookingDetails.setBookingStatus(AppConstants.BOOKING_STATUS_PENDING);
        bookingDetails.setDescription(bookserviceDto.getDescription());
        bookingDetails.setCreatedAt(LocalDateTime.now());
        bookingDetails.setLocation(bookserviceDto.getLocation());
        bookingDetails.setActive(true);
        _bookingDetailsRepository.save(bookingDetails);
        _notificationService.createNotification(bookingDetails);
        System.out.println("Booking request created successfully for service: " +
                bookingDetails.getService().getCompanyName() + " with ID: " + bookingDetails.getId());
    }

    public List<BookingDetailsDto> getBookingRequests(long serviceProviderId) {
        var bookService = _bookingDetailsRepository.fetchBookingsWithCustomerInfo(serviceProviderId);
        return _bookingDetailsMapper.toDtoList(bookService);
    }

    public List<BookingDetailsDto> getMyBookings(long userId) {
        List<BookingDetails> bookService = _bookingDetailsRepository.fetchBookingsWithServiceProviderInfo(userId);
        return _bookingDetailsMapper.toDtoList(bookService);
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

    public List<BookingDetailsDto> getRecentBookings() {
        List<BookingDetails> recentBookings = _bookingDetailsRepository.findTop10ByOrderByCreatedAtDesc();
        return _bookingDetailsMapper.toDtoList(recentBookings);
    }

}
