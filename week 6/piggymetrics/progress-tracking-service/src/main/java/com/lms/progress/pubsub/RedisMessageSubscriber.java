package com.lms.progress.pubsub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMessageSubscriber implements MessageListener {
    private final RedisMessageListenerContainer container;
    private final SimpMessagingTemplate messagingTemplate;

    @PostConstruct
    public void subscribe() {
        container.addMessageListener(this, new ChannelTopic("chat"));
        container.addMessageListener(this, new ChannelTopic("quiz"));
        container.addMessageListener(this, new ChannelTopic("notification"));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        String payload = new String(message.getBody(), StandardCharsets.UTF_8);
        log.debug("Received Redis pub/sub message on channel {}: {}", channel, payload);
        // Route to appropriate WebSocket topic/queue
        switch (channel) {
            case "chat":
                // Expect payload to contain the destination and message, e.g. "/topic/lesson.42.chat|{json}"
                int sep = payload.indexOf('|');
                if (sep > 0) {
                    String dest = payload.substring(0, sep);
                    String msg = payload.substring(sep + 1);
                    messagingTemplate.convertAndSend(dest, msg);
                }
                break;
            case "quiz":
                sep = payload.indexOf('|');
                if (sep > 0) {
                    String dest = payload.substring(0, sep);
                    String msg = payload.substring(sep + 1);
                    messagingTemplate.convertAndSend(dest, msg);
                }
                break;
            case "notification":
                sep = payload.indexOf('|');
                if (sep > 0) {
                    String dest = payload.substring(0, sep);
                    String msg = payload.substring(sep + 1);
                    messagingTemplate.convertAndSend(dest, msg);
                }
                break;
            default:
                log.warn("Unknown Redis pub/sub channel: {}", channel);
        }
    }
} 