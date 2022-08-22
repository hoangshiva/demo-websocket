package com.example.demowebsocket.mongo.repository;

import com.example.demowebsocket.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, Long> {
}
