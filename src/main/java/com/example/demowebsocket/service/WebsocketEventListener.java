package com.example.demowebsocket.service;

import com.example.demowebsocket.domain.ChatRoomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebsocketEventListener {

    @Autowired
    private ChatRoomService chatRoomService;

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Long chatRoomId = Long.valueOf(headers.getNativeHeader("chatRoomId").get(0));
        headers.getSessionAttributes().put("chatRoomId", chatRoomId);
        ChatRoomUser joiningUser = ChatRoomUser.builder()
                .chatRoomId(chatRoomId)
                .username(event.getUser().getName())
                .build();

        chatRoomService.join(joiningUser, chatRoomService.getById(chatRoomId));
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String chatRoomId = headers.getSessionAttributes().get("chatRoomId").toString();
        ChatRoomUser leavingUser = new ChatRoomUser(event.getUser().getName());

        chatRoomService.leave(leavingUser, chatRoomService.findById(chatRoomId));
    }
}
