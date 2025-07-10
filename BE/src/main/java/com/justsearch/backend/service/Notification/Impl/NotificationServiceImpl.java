package com.justsearch.backend.service.Notification.Impl;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.justsearch.backend.dto.NotificationDto;
import com.justsearch.backend.model.Notification;   
import com.justsearch.backend.repository.NotificationRepository;
import com.justsearch.backend.service.Notification.NotificationService;

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
