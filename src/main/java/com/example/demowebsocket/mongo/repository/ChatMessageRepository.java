package com.example.demowebsocket.mongo.repository;

import com.example.demowebsocket.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findAllByRoomIdAndUserFrom(String roomId, String userFrom);
}
