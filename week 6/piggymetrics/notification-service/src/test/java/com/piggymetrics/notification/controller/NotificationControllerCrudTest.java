package com.piggymetrics.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piggymetrics.notification.domain.Notification;
import com.piggymetrics.notification.domain.Notification.NotificationType;
import com.piggymetrics.notification.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@EnableJpaRepositories(basePackages = "com.piggymetrics.notification.repository")
@EntityScan(basePackages = "com.piggymetrics.notification.domain")
@ActiveProfiles("test")
class NotificationControllerCrudTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private EntityManager entityManager;

    private UUID userId;

    @BeforeEach
    void setup() {
        notificationRepository.deleteAllInBatch();
        userId = UUID.randomUUID();
    }

    @Test
    @Commit
    void createAndGetNotification() throws Exception {
        Notification notification = Notification.builder()
                .userId(userId)
                .type(NotificationType.SYSTEM)
                .message("Welcome!")
                .status(Notification.Status.UNREAD)
                .createdAt(java.time.LocalDateTime.now())
                .build();
        String json = objectMapper.writeValueAsString(notification);

        // Create
        String response = mockMvc.perform(post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.message").value("Welcome!"))
                .andReturn().getResponse().getContentAsString();
        Notification created = objectMapper.readValue(response, Notification.class);

        // Debug: print all notifications in the repository
        System.out.println("All notifications in DB after POST: " + notificationRepository.findAll());

        // Get by ID
        mockMvc.perform(get("/notifications/" + created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Welcome!"));
    }

    @Test
    void getNotificationsByUser() throws Exception {
        Notification notification = Notification.builder()
                .userId(userId)
                .type(NotificationType.COURSE)
                .message("Course update")
                .status(Notification.Status.UNREAD)
                .createdAt(java.time.LocalDateTime.now())
                .build();
        String json = objectMapper.writeValueAsString(notification);
        // Create via HTTP
        String response = mockMvc.perform(post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Notification created = objectMapper.readValue(response, Notification.class);
        // Now test GET by user
        mockMvc.perform(get("/notifications/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(created.getId().toString()));
    }

    @Test
    void markAsRead() throws Exception {
        Notification notification = Notification.builder()
                .userId(userId)
                .type(NotificationType.ASSESSMENT)
                .message("Assessment due")
                .status(Notification.Status.UNREAD)
                .createdAt(java.time.LocalDateTime.now())
                .build();
        String json = objectMapper.writeValueAsString(notification);
        // Create via HTTP
        String response = mockMvc.perform(post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Notification created = objectMapper.readValue(response, Notification.class);
        // Mark as read
        mockMvc.perform(patch("/notifications/" + created.getId() + "/read"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("READ"));
    }

    @Test
    void deleteNotification() throws Exception {
        Notification notification = Notification.builder()
                .userId(userId)
                .type(NotificationType.REMINDER)
                .message("Don't forget!")
                .status(Notification.Status.UNREAD)
                .createdAt(java.time.LocalDateTime.now())
                .build();
        String json = objectMapper.writeValueAsString(notification);
        // Create via HTTP
        String response = mockMvc.perform(post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Notification created = objectMapper.readValue(response, Notification.class);
        // Ensure exists
        mockMvc.perform(get("/notifications/" + created.getId()))
                .andExpect(status().isOk());
        // Delete
        mockMvc.perform(delete("/notifications/" + created.getId()))
                .andExpect(status().isNoContent());
        // Ensure deleted
        mockMvc.perform(get("/notifications/" + created.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getNotificationNotFound() throws Exception {
        mockMvc.perform(get("/notifications/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
} 