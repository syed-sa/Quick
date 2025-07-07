package Quick.service.ServiceBooking.impl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import Quick.constants.AppConstants;
import Quick.dto.BookServiceDto;
import Quick.repository.BookingDetailsRepository;
import Quick.repository.ServicesRepository;
import Quick.repository.UserRepository;
import Quick.service.ServiceBooking.BookService;
import Quick.model.BookingDetails;
import Quick.model.User;



@Service
public class BookServiceImpl implements BookService {

    private BookingDetailsRepository _bookingDetailsRepository;
    private ServicesRepository _servicesRepository;
    private ModelMapper _bookingDetailsMapper;
    private UserRepository _userRepository;

    public BookServiceImpl(BookingDetailsRepository bookingDetailsRepository, ServicesRepository servicesRepository,
            ModelMapper bookingDetailsMapper, UserRepository userRepository) {
        _bookingDetailsRepository = bookingDetailsRepository;
        _servicesRepository = servicesRepository;
        _userRepository = userRepository;
        _bookingDetailsMapper = bookingDetailsMapper;
    }

    public void createBookingRequest(BookServiceDto bookserviceDto) {
        if (bookserviceDto == null) {
            throw new IllegalArgumentException("service must not be null");
        }
        User user = _userRepository.findById(bookserviceDto.getCustomerId()).orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
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

    }

    public List<BookServiceDto> getBookingRequests(long userId) {
        List<BookingDetails> bookingDetails = _bookingDetailsRepository.findAllByServiceProviderId(userId);
       List<BookServiceDto> bookService = bookingDetails.stream()
               .map(source -> _bookingDetailsMapper.map(source, BookServiceDto.class))
               .collect(Collectors.toList());
        return bookService;
    }

}
