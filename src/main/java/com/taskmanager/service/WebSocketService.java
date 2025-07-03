package com.taskmanager.service;

import com.taskmanager.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void broadcastTaskUpdate(Task task, String action) {
        Map<String, Object> message = new HashMap<>();
        message.put("action", action);
        message.put("task", task);
        message.put("timestamp", LocalDateTime.now());

        messagingTemplate.convertAndSend("/topic/tasks", message);
    }

    public void broadcastTaskDelete(Long taskId) {
        Map<String, Object> message = new HashMap<>();
        message.put("action", "DELETE");
        message.put("taskId", taskId);
        message.put("timestamp", LocalDateTime.now());

        messagingTemplate.convertAndSend("/topic/tasks", message);
    }
}