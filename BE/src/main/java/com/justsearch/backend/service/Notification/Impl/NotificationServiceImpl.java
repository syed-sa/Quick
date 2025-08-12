package com.justsearch.backend.service.Notification.Impl;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.justsearch.backend.constants.AppConstants;
import com.justsearch.backend.dto.NotificationDto;
import com.justsearch.backend.dto.ToastMessage;
import com.justsearch.backend.mapper.NotificationMapper;
import com.justsearch.backend.model.BookingDetails;
import com.justsearch.backend.model.Notification;
import com.justsearch.backend.model.User;
import com.justsearch.backend.repository.NotificationRepository;
import com.justsearch.backend.repository.UserRepository;
import com.justsearch.backend.service.Notification.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository _notificationRepository;
    private final NotificationMapper _notificationMapper;
    private UserRepository _userRepository;

     private final SimpMessagingTemplate messagingTemplate;   // <-- new


    public NotificationServiceImpl(NotificationRepository notificationRepository, NotificationMapper notificationMapper,
    SimpMessagingTemplate simpMessagingTemplate,UserRepository userRepository) {
        _notificationRepository = notificationRepository;
        _notificationMapper = notificationMapper;
        messagingTemplate = simpMessagingTemplate;
        _userRepository = userRepository;
    }

    public List<NotificationDto> getNotificationsForUser(Long userId) {
        // Fetch notifications from the repository and convert them to DTOs
        List<Notification> notifications = _notificationRepository.findAllByUserId(userId); // Filter out read
                                                                                            // notifications
        return _notificationMapper.toDtoList(notifications);

    }

    @PostMapping("/read/{notificationId}")
    public void markNotificationAsRead(@PathVariable Long notificationId) {
        // Find the notification by ID and update its status
        Notification notification = _notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        notification.setRead(true);
        _notificationRepository.save(notification);
    }

    public void createNotification(BookingDetails bookingDetails) {
        Notification notification = new Notification();
        notification.setUserId(bookingDetails.getService().getServiceProvider().getId());
        notification.setMessage("New booking request from customer: " + bookingDetails.getCustomer().getId());
        notification.setRead(false);
        notification.setTimestamp(LocalDateTime.now());
        notification.setNotificationTitle(AppConstants.NEW_BOOKING_REQUEST);
        notification.setNotificationType(AppConstants.BOOKING_STATUS_PENDING);
        notification.setBookingId(bookingDetails.getId());
        _notificationRepository.save(notification);
        pushToast(notification);
    }

    public void createBookingRejectedNotification(BookingDetails bookingDetails) {
        Notification notification = new Notification();
        notification.setUserId(bookingDetails.getCustomer().getId());
        notification.setMessage("Your booking request for service: " + bookingDetails.getService().getCompanyName() + " has been "
                + bookingDetails.getBookingStatus());
        notification.setRead(false);
        notification.setTimestamp(LocalDateTime.now());
        notification.setNotificationTitle(AppConstants.BOOKING_STATUS_REJECTED);
        notification.setNotificationType(AppConstants.BOOKING_STATUS_REJECTED);
        notification.setBookingId(bookingDetails.getId());
        _notificationRepository.save(notification);
        pushToast(notification);

    }

private void pushToast(Notification n) {
    try {
        ToastMessage toastMessage = new ToastMessage(
                n.getId(),
                n.getNotificationTitle(),
                n.getMessage(),
                n.getTimestamp()
        );
        
        System.out.println("Sending toast to user: " + n.getUserId());
        System.out.println("Toast message: " + toastMessage);
         User user = _userRepository.findById(n.getUserId()).orElse(new User());
        messagingTemplate.convertAndSendToUser(
               user.getEmail(),           
                "/queue/toast",    
                toastMessage
        );
        
    } catch (Exception e) {
        System.err.println("Error sending toast notification: " + e.getMessage());
        e.printStackTrace();
    }
}
}
