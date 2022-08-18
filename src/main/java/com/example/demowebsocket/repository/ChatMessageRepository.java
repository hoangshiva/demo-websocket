package com.example.demowebsocket.repository;

import com.example.demowebsocket.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {


    List<ChatMessage> findAllByRoomIdAndUserFrom(Long roomId, String userFrom);
}
