package Quick.repository;

import java.util.List;

import Quick.model.Notification;

public interface NotificationRepository extends org.springframework.data.jpa.repository.JpaRepository<Quick.model.Notification, Long> {
    // Additional query methods can be defined here if needed
    // For example, to find notifications by userId:
   Notification findByUserId(Long userId);

   List<Notification> findAllByUserId(Long userId);
}
