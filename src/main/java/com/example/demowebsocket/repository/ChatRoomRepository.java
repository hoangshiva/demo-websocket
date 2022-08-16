package com.example.demowebsocket.repository;

import com.example.demowebsocket.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
