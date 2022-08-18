package com.example.demowebsocket.repository;

import com.example.demowebsocket.domain.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

    Optional<ChatRoomUser> findFirstByChatRoomIdAndUsername(Long chatRoomId, String username);
}
