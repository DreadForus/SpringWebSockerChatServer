package com.example.websocketdemo.repository;

import com.example.websocketdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ParticipantRepository {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	private Map<String, User> activeSessions = new ConcurrentHashMap<>();

	public void add(String sessionId, User user) {
		activeSessions.put(sessionId, user);

		messagingTemplate.convertAndSend("/topic/chat.login", user);
	}

	public User getParticipant(String sessionId) {
		return activeSessions.get(sessionId);
	}

	public void removeParticipant(String sessionId) {
		User user = activeSessions.get(sessionId);

		activeSessions.remove(sessionId);

		messagingTemplate.convertAndSend("/topic/chat.logout", user);
	}

	public Map<String, User> getActiveSessions() {
		return activeSessions;
	}
}
