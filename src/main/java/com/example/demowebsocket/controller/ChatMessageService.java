package com.example.demowebsocket.controller;

import com.example.demowebsocket.domain.ChatMessage;
import com.example.demowebsocket.repository.ChatMessageRepository;
import com.example.demowebsocket.repository.ChatRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public void appendInstantMessageToConversations(ChatMessage instantMessage) {
        if (instantMessage.isFromAdmin() || instantMessage.isPublic()) {
            ChatRoom chatRoom = chatRoomService.findById(instantMessage.getChatRoomId());

            chatRoom.getConnectedUsers().forEach(connectedUser -> {
                instantMessage.setUsername(connectedUser.getUsername());
                instantMessageRepository.save(instantMessage);
            });
        } else {
            instantMessage.setUsername(instantMessage.getFromUser());
            instantMessageRepository.save(instantMessage);

            instantMessage.setUsername(instantMessage.getToUser());
            instantMessageRepository.save(instantMessage);
        }
    }
}
