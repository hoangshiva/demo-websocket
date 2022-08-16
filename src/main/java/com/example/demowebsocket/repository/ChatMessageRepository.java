package com.example.demowebsocket.repository;

import com.example.demowebsocket.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
