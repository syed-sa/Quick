package com.justsearch.backend.service.Notification;
import java.util.List;

import com.justsearch.backend.dto.NotificationDto;
public interface NotificationService {

    public List<NotificationDto> getNotificationsForUser(Long userId);

    }

