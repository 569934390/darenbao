package com.club.web.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.club.framework.log.ClubLogManager;
import com.club.web.message.service.MessageService;

@Configurable
public class WebSocketHandlerExtend extends AbstractWebSocketHandler {
	
    private static final ClubLogManager logger = ClubLogManager.getLogger(WebSocketHandlerExtend.class);
	
    @Autowired private WebSocketManager manager;
    @Autowired private MessageService messageService;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.debug("链接成功......");
		manager.addWebSocketUser(session);
		manager.send(session, messageService.messageCount());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.debug("链接关闭......" + status.toString());
		manager.removeWebSocketUser(session);
	}
}
