package com.justsearch.backend.service.ServiceBooking.impl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.justsearch.backend.constants.AppConstants;
import com.justsearch.backend.dto.BookingDetailsDto;    
import com.justsearch.backend.model.BookingDetails;
import com.justsearch.backend.model.Notification;
import com.justsearch.backend.model.User;
import com.justsearch.backend.repository.BookingDetailsRepository;
import com.justsearch.backend.repository.NotificationRepository;
import com.justsearch.backend.repository.ServicesRepository;
import com.justsearch.backend.repository.UserRepository;
import com.justsearch.backend.service.ServiceBooking.BookService;



@Service
public class BookServiceImpl implements BookService {

    private BookingDetailsRepository _bookingDetailsRepository;
    private ServicesRepository _servicesRepository;
   private UserRepository _userRepository;
    private NotificationRepository _notificationRepository;

    public BookServiceImpl(BookingDetailsRepository bookingDetailsRepository, ServicesRepository servicesRepository,
             UserRepository userRepository,
            NotificationRepository notificationRepository) {
        _bookingDetailsRepository = bookingDetailsRepository;
        _servicesRepository = servicesRepository;
        _userRepository = userRepository;
        _notificationRepository = notificationRepository;
    }

    public void createBookingRequest(BookingDetailsDto bookserviceDto) {
        if (bookserviceDto == null) {
            throw new IllegalArgumentException("service must not be null");
        }
        User user = _userRepository.findById(bookserviceDto.getCustomerId())
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
        createNotification(bookingDetails);
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

    private void createNotification(BookingDetails bookingDetails) {
        Notification notification = new Notification();
        notification.setUserId(bookingDetails.getServiceProviderId());
        notification.setMessage("New booking request from customer: " + bookingDetails.getCustomerId());
        notification.setRead(false);
        notification.setTimestamp(LocalDateTime.now());
        notification.setNotificationTitle(AppConstants.NEW_BOOKING_REQUEST);
        notification.setNotificationType(AppConstants.BOOKING_REQUEST);
        notification.setBookingId(bookingDetails.getId());
        _notificationRepository.save(notification);
    }

    public void updateBooking(long bookingId, String status) {
        BookingDetails bookingDetails = _bookingDetailsRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking ID"));
        bookingDetails.setBookingStatus(status);
        if(status.equals(AppConstants.BOOKING_STATUS_CANCELLED) || status.equals(AppConstants.BOOKING_STATUS_REJECTED)) {
            createBookingRejectedNotification(bookingDetails);
            bookingDetails.setActive(false);
        }
        _bookingDetailsRepository.save(bookingDetails);
    }
    private void createBookingRejectedNotification(BookingDetails bookingDetails) {
        Notification notification = new Notification();
        notification.setUserId(bookingDetails.getCustomerId());
        notification.setMessage("Your booking request for service: " + bookingDetails.getServiceName() + " has been "
                + bookingDetails.getBookingStatus());
        notification.setRead(false);
        notification.setTimestamp(LocalDateTime.now());
        notification.setNotificationTitle(AppConstants.BOOKING_STATUS_REJECTED);
        notification.setNotificationType(AppConstants.BOOKING_STATUS_REJECTED);
        notification.setBookingId(bookingDetails.getId());
        _notificationRepository.save(notification);
    }
}
