package com.lms.progress.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationMessage {
    public enum MessageType {
        PROGRESS, ANNOUNCEMENT, QUIZ, ASSIGNMENT
    }
    private MessageType type;
    private String content;
    private String recipient;
    private Instant timestamp;
    private Long lessonId;
    private Long quizId;
} 