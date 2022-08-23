package com.example.demowebsocket.mongo.repository;

import com.example.demowebsocket.domain.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
}
