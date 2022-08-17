package com.example.demowebsocket.service;

import com.example.demowebsocket.domain.ChatRoom;
import com.example.demowebsocket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Transactional(readOnly = true)
    public List<ChatRoom> list() {
        return chatRoomRepository.findAll();
    }


}
