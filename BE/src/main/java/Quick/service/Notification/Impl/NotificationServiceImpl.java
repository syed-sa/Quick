package Quick.service.Notification.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import Quick.dto.NotificationDto;
import Quick.model.Notification;
import Quick.repository.NotificationRepository;
import Quick.service.Notification.NotificationService;
@Service
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository _notificationRepository;
    private final ModelMapper _notificationMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository, ModelMapper notificationMapper) {
        _notificationRepository = notificationRepository;
        _notificationMapper = notificationMapper;
    }
    public List<NotificationDto> getNotificationsForUser(Long userId) {
        // Fetch notifications from the repository and convert them to DTOs
        List<Notification> notifications = _notificationRepository.findAllByUserId(userId);
        return notifications.stream()
                .map(notification -> _notificationMapper.map(notification, NotificationDto.class))
                .collect(Collectors.toList());
                
    }
}
