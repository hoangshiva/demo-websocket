package com.example.demowebsocket.service;

import com.example.demowebsocket.domain.ChatMessage;
import com.example.demowebsocket.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> findAllInstantMessagesFor(String username, Long chatRoomId) {
        return chatMessageRepository.findAllByRoomIdAndUserFrom(chatRoomId, username);
    }
}
