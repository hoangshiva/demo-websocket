package com.example.demowebsocket.service;

import com.example.demowebsocket.command.ChatRoomCreateCommand;
import com.example.demowebsocket.domain.ChatMessage;
import com.example.demowebsocket.domain.ChatRoom;
import com.example.demowebsocket.domain.ChatRoomUser;
import com.example.demowebsocket.repository.ChatMessageRepository;
import com.example.demowebsocket.repository.ChatRoomRepository;
import com.example.demowebsocket.utils.SystemMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final SimpMessagingTemplate webSocketMessagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

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

    public ChatRoom getById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElse(null);
    }


    public ChatRoom join(ChatRoomUser joiningUser, ChatRoom chatRoom) {
        sendPublicMessage(SystemMessages.welcome(chatRoom.getId(), joiningUser.getUsername()));
        updateConnectedUsersViaWebSocket(chatRoom);
        return chatRoom;
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
        webSocketMessagingTemplate.convertAndSend(publicMessages(instantMessage.getRoomId()),
                instantMessage);
        chatMessageRepository.save(instantMessage);
    }

    public void sendPrivateMessage(ChatMessage instantMessage) {
        webSocketMessagingTemplate.convertAndSendToUser(
                instantMessage.getUserFrom(), privateMessages(instantMessage.getRoomId()),
                instantMessage);

        webSocketMessagingTemplate.convertAndSendToUser(
                instantMessage.getUserTo(),privateMessages(instantMessage.getRoomId()),
                instantMessage);
        chatMessageRepository.save(instantMessage);
    }

    private void updateConnectedUsersViaWebSocket(ChatRoom chatRoom) {
        webSocketMessagingTemplate.convertAndSend(
                connectedUsers(chatRoom.getId()),
                chatRoom.getChatRoomUsers());
    }

    public static String publicMessages(Long chatRoomId) {
        return "/topic/" + chatRoomId + ".public.messages";
    }

    public static String privateMessages(Long chatRoomId) {
        return "/queue/" + chatRoomId + ".private.messages";
    }

    public static String connectedUsers(Long chatRoomId) {
        return "/topic/" + chatRoomId + ".connected.users";
    }


}
