package com.example.demowebsocket.mongo.repository;

import com.example.demowebsocket.domain.ChatRoomUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomUserRepository extends MongoRepository<ChatRoomUser, String> {

    List<ChatRoomUser> findAllByChatRoomId(String chatRoomId);

    Optional<ChatRoomUser> findFirstByChatRoomIdAndUsername(String chatRoomId, String username);
}
