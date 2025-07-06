package Quick.service.ServiceBooking;
import java.util.List;
import Quick.dto.BookServiceDto;

public interface BookService {

     void createBookingRequest(BookServiceDto bookserviceDto);
     List<BookServiceDto> getBookingRequests(long userId);
} 