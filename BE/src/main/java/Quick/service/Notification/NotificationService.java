package Quick.service.Notification;

import java.util.List;

import Quick.dto.NotificationDto;

public interface NotificationService {

    public List<NotificationDto> getNotificationsForUser(Long userId);

    }

