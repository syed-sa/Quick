package com.justsearch.backend.dto;

import java.time.LocalDateTime;

public record ToastMessage(
        Long notificationId,
        String title,
        String body,
        LocalDateTime timestamp
) {}