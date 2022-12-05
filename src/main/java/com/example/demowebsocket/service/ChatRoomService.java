package com.example.demowebsocket.service;

import com.example.demowebsocket.command.ChatRoomCreateCommand;
import com.example.demowebsocket.domain.ChatMessage;
import com.example.demowebsocket.domain.ChatRoom;
import com.example.demowebsocket.domain.ChatRoomUser;
import com.example.demowebsocket.mongo.repository.ChatMessageRepository;
import com.example.demowebsocket.mongo.repository.ChatRoomRepository;
import com.example.demowebsocket.mongo.repository.ChatRoomUserRepository;
import com.example.demowebsocket.utils.SystemMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final SimpMessagingTemplate webSocketMessagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;

    @Transactional(readOnly = true)
    public List<ChatRoom> list() {
        return chatRoomRepository.findAll();
    }

    @Transactional
    public ChatRoom create(@RequestBody ChatRoomCreateCommand command, String username) {
        ChatRoom chatRoom = ChatRoom.builder()
                .chatName(command.getName())
                .description(command.getDescription())
                .status("PENDING")
                .username(username)
                .build();
        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom getById(String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElse(null);
    }


    public void join(ChatRoomUser joiningUser, ChatRoom chatRoom) {
        sendPublicMessage(SystemMessages.welcome(chatRoom.getId(), joiningUser.getUsername()));
        updateConnectedUsersViaWebSocket(chatRoom);
    }


//    public ChatRoom leave(ChatRoomUser leavingUser, ChatRoom chatRoom) {
//        sendPublicMessage(SystemMessages.goodbye(chatRoom.getId(), leavingUser.getUsername()));
//
//        chatRoom.removeUser(leavingUser);
//        chatRoomRepository.save(chatRoom);
//
//        updateConnectedUsersViaWebSocket(chatRoom);
//        return chatRoom;
//    }

    public void sendPublicMessage(ChatMessage instantMessage) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("auto-delete", "true");
        webSocketMessagingTemplate.convertAndSend(publicMessages(instantMessage.getRoomId()),
                instantMessage);
        chatMessageRepository.save(instantMessage);
    }

    public void sendPrivateMessage(ChatMessage instantMessage) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("auto-delete", "true");
        webSocketMessagingTemplate.convertAndSendToUser(
                instantMessage.getUserFrom(), privateMessages(instantMessage.getRoomId()),
                instantMessage);

        webSocketMessagingTemplate.convertAndSendToUser(
                instantMessage.getUserTo(), privateMessages(instantMessage.getRoomId()),
                instantMessage);
        chatMessageRepository.save(instantMessage);
    }

    private void updateConnectedUsersViaWebSocket(ChatRoom chatRoom) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("auto-delete", "true");
        List<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findAllByChatRoomId(chatRoom.getId());
        webSocketMessagingTemplate.convertAndSend(connectedUsers(chatRoom.getId()), chatRoomUsers);
    }

    public static String publicMessages(String chatRoomId) {
        return "/topic/" + chatRoomId + ".public.messages";
    }

    public static String privateMessages(String chatRoomId) {
        return "/exchange/amq.direct/" + chatRoomId + ".private.messages";
    }

    public static String connectedUsers(String chatRoomId) {
        return "/topic/" + chatRoomId + ".connected.users";
    }


}
