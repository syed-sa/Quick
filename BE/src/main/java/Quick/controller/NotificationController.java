package Quick.controller;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Quick.dto.NotificationDto;
import Quick.service.Notification.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private NotificationService _notificationService;

    public NotificationController(NotificationService notificationService) {
        _notificationService = notificationService;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationDto>> getNotificationsForUser(@PathVariable Long userId) {
        List<NotificationDto> notifications = _notificationService.getNotificationsForUser(userId);
        return ResponseEntity.ok(notifications);
    }
}