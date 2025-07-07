package Quick.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Quick.model.BookingDetails;
@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails,Long> {

    List<BookingDetails> findAllByServiceProviderId(long userId);
    List<BookingDetails> findAllByCustomerId(long userId);
} 