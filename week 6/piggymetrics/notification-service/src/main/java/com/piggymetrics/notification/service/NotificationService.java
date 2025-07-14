package com.piggymetrics.notification.service;

import com.piggymetrics.notification.domain.Notification;
import com.piggymetrics.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public Notification createNotification(Notification notification) {
        notification.setStatus(Notification.Status.UNREAD);
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public Optional<Notification> getNotification(UUID id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> getNotificationsByUser(UUID userId) {
        return notificationRepository.findByUserId(userId);
    }

    public Optional<Notification> markAsRead(UUID id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent()) {
            Notification n = notification.get();
            n.setStatus(Notification.Status.READ);
            n.setReadAt(LocalDateTime.now());
            return Optional.of(notificationRepository.save(n));
        }
        return Optional.empty();
    }

    public boolean deleteNotification(UUID id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 