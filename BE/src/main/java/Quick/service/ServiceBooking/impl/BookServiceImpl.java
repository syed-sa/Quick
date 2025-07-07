package Quick.service.ServiceBooking.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import Quick.constants.AppConstants;
import Quick.dto.BookServiceDto;
import Quick.repository.BookingDetailsRepository;
import Quick.repository.NotificationRepository;
import Quick.repository.ServicesRepository;
import Quick.repository.UserRepository;
import Quick.service.ServiceBooking.BookService;
import Quick.model.BookingDetails;
import Quick.model.Notification;
import Quick.model.User;

@Service
public class BookServiceImpl implements BookService {

    private BookingDetailsRepository _bookingDetailsRepository;
    private ServicesRepository _servicesRepository;
    private ModelMapper _bookingDetailsMapper;
    private UserRepository _userRepository;
    private NotificationRepository _notificationRepository;

    public BookServiceImpl(BookingDetailsRepository bookingDetailsRepository, ServicesRepository servicesRepository,
            ModelMapper bookingDetailsMapper, UserRepository userRepository,
            NotificationRepository notificationRepository) {
        _bookingDetailsRepository = bookingDetailsRepository;
        _servicesRepository = servicesRepository;
        _bookingDetailsMapper = bookingDetailsMapper;
        _userRepository = userRepository;
        _notificationRepository = notificationRepository;
    }

    public void createBookingRequest(BookServiceDto bookserviceDto) {
        if (bookserviceDto == null) {
            throw new IllegalArgumentException("service must not be null");
        }
        User user = _userRepository.findById(bookserviceDto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
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
        bookingDetails.phone = user.getPhone();
        bookingDetails.email = user.getEmail();
        _bookingDetailsRepository.save(bookingDetails);
        createNotification(bookingDetails);
        System.out.println("Booking request created successfully for service: " + bookingDetails.serviceName);

    }

    public List<BookServiceDto> getBookingRequests(long userId) {
        List<BookingDetails> bookingDetails = _bookingDetailsRepository.findAllByServiceProviderId(userId);
        List<BookServiceDto> bookService = bookingDetails.stream()
                .map(source -> _bookingDetailsMapper.map(source, BookServiceDto.class))
                .collect(Collectors.toList());
        return bookService;
    }

    public List<BookServiceDto> getMyBookings(long userId) {
        List<BookingDetails> bookingDetails = _bookingDetailsRepository.findAllByCustomerId(userId);
        List<BookServiceDto> bookService = bookingDetails.stream()
                .map(source -> _bookingDetailsMapper.map(source, BookServiceDto.class))
                .collect(Collectors.toList());
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
        notification.setTimestamp(LocalDateTime.now());
        _notificationRepository.save(notification);
    }
}
