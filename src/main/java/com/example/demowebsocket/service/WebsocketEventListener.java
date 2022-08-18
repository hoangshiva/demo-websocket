package com.example.demowebsocket.service;

import com.example.demowebsocket.domain.ChatRoom;
import com.example.demowebsocket.domain.ChatRoomUser;
import com.example.demowebsocket.repository.ChatRoomRepository;
import com.example.demowebsocket.repository.ChatRoomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Optional;

@Component
public class WebsocketEventListener {

    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatRoomUserRepository chatRoomUserRepository;

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Long chatRoomId = Long.valueOf(headers.getNativeHeader("chatRoomId").get(0));
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(chatRoomId);
        if (chatRoomOptional.isPresent()) {
            ChatRoom chatRoom = chatRoomOptional.get();
            headers.getSessionAttributes().put("chatRoomId", chatRoomId);
            ChatRoomUser joiningUser;
            Optional<ChatRoomUser> chatRoomUserOptional = chatRoomUserRepository.findFirstByChatRoomIdAndUsername(chatRoomId, event.getUser().getName());
            if (!chatRoomUserOptional.isPresent()) {
                joiningUser = ChatRoomUser.builder()
                        .chatRoom(chatRoomOptional.get())
                        .username(event.getUser().getName())
                        .build();
                chatRoomUserRepository.save(joiningUser);
                chatRoom.addUser(joiningUser);
            } else {
                joiningUser = chatRoomUserOptional.get();
            }
            chatRoomService.join(joiningUser, chatRoom);
        }

    }

//    @EventListener
//    private void handleSessionDisconnect(SessionDisconnectEvent event) {
//        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
//        String chatRoomId = headers.getSessionAttributes().get("chatRoomId").toString();
//        ChatRoomUser leavingUser = new ChatRoomUser(event.getUser().getName());
//
//        chatRoomService.leave(leavingUser, chatRoomService.findById(chatRoomId));
//    }
}
