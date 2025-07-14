package com.lms.progress.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.progress.dto.NotificationMessage;
import com.lms.progress.pubsub.RedisMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Slf4j
@Controller
public class NotificationRealtimeController {
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisMessagePublisher redisPublisher;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NotificationRealtimeController(SimpMessagingTemplate messagingTemplate, RedisMessagePublisher redisPublisher) {
        this.messagingTemplate = messagingTemplate;
        this.redisPublisher = redisPublisher;
    }

    private void publishAndSend(String recipient, NotificationMessage message) {
        String dest = "/queue/user." + recipient + ".notifications";
        try {
            String json = objectMapper.writeValueAsString(message);
            redisPublisher.publish("notification", dest + "|" + json);
        } catch (Exception e) {
            log.error("Failed to publish notification message to Redis", e);
        }
        messagingTemplate.convertAndSend(dest, message);
    }

    @MessageMapping("/notify.progress")
    public void notifyProgress(@Payload NotificationMessage message) {
        message.setType(NotificationMessage.MessageType.PROGRESS);
        message.setTimestamp(Instant.now());
        publishAndSend(message.getRecipient(), message);
    }

    @MessageMapping("/notify.announcement")
    public void notifyAnnouncement(@Payload NotificationMessage message) {
        message.setType(NotificationMessage.MessageType.ANNOUNCEMENT);
        message.setTimestamp(Instant.now());
        publishAndSend(message.getRecipient(), message);
    }

    @MessageMapping("/notify.quiz")
    public void notifyQuiz(@Payload NotificationMessage message) {
        message.setType(NotificationMessage.MessageType.QUIZ);
        message.setTimestamp(Instant.now());
        publishAndSend(message.getRecipient(), message);
    }

    @MessageMapping("/notify.assignment")
    public void notifyAssignment(@Payload NotificationMessage message) {
        message.setType(NotificationMessage.MessageType.ASSIGNMENT);
        message.setTimestamp(Instant.now());
        publishAndSend(message.getRecipient(), message);
    }
} 