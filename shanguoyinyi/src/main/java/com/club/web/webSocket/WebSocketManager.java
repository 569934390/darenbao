package com.club.web.webSocket;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketManager {
	
	private Logger logger = LoggerFactory.getLogger(WebSocketManager.class);

	private final ArrayList<WebSocketSession> sessionList = new ArrayList<>();

	public void addWebSocketUser(WebSocketSession session) {
		sessionList.add(session);
	}

	public void removeWebSocketUser(WebSocketSession session) {
		sessionList.remove(session);
	}

	public void send(WebSocketSession session, int message) {
		send(session, message+"");
	}

	public void send(WebSocketSession session, String message) {
		try {
			if (session.isOpen())
				session.sendMessage(new TextMessage(message));
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	public void send(int message) {
		send(message+"");
	}

	public void send(String message) {
		for (WebSocketSession session : sessionList)
			send(session, message);
	}
}
