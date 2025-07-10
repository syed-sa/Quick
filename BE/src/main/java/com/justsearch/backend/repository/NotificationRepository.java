package com.justsearch.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.justsearch.backend.model.Notification;
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Additional query methods can be defined here if needed
    // For example, to find notifications by userId:
   Notification findByUserId(Long userId);

   List<Notification> findAllByUserId(Long userId);
}
