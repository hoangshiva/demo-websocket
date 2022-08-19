package com.example.demowebsocket.controller;


import com.example.demowebsocket.command.ChatRoomCreateCommand;
import com.example.demowebsocket.command.MessagePayload;
import com.example.demowebsocket.domain.ChatMessage;
import com.example.demowebsocket.domain.ChatRoom;
import com.example.demowebsocket.domain.ChatRoomUser;
import com.example.demowebsocket.service.ChatMessageService;
import com.example.demowebsocket.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class ChatRoomController {

	@Autowired
	private ChatRoomService chatRoomService;

	@Autowired
	private ChatMessageService chatMessageService;


	@RequestMapping(path = "/chatroom", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.CREATED)
	public ChatRoom createChatRoom(@RequestBody ChatRoomCreateCommand command, Principal principal) {
		return chatRoomService.create(command, principal.getName());
	}

	@RequestMapping("/chatroom/{chatRoomId}")
	public ModelAndView join(@PathVariable Long chatRoomId, Principal principal) {
		ModelAndView modelAndView = new ModelAndView("chatroom");
		modelAndView.addObject("chatRoom", chatRoomService.getById(chatRoomId));
		return modelAndView;
	}

	@SubscribeMapping("/connected.users")
	public List<ChatRoomUser> listChatRoomConnectedUsersOnSubscribe(SimpMessageHeaderAccessor headerAccessor) {
		Long chatRoomId = Long.valueOf(headerAccessor.getSessionAttributes().get("chatRoomId").toString());
		return chatRoomService.getById(chatRoomId).getChatRoomUsers();
	}

	@SubscribeMapping("/old.messages")
	public List<ChatMessage> listOldMessagesFromUserOnSubscribe(Principal principal,
																SimpMessageHeaderAccessor headerAccessor) {
		Long chatRoomId = Long.valueOf(headerAccessor.getSessionAttributes().get("chatRoomId").toString());
		return chatMessageService.findAllInstantMessagesFor(principal.getName(), chatRoomId);
	}

	@MessageMapping("/send.message")
	public void sendMessage(@Payload MessagePayload instantMessage, Principal principal,
							SimpMessageHeaderAccessor headerAccessor) {
		Long chatRoomId = Long.valueOf(headerAccessor.getSessionAttributes().get("chatRoomId").toString());
		ChatMessage chatMessage;
		if (instantMessage.getIsPublic() != null && instantMessage.getIsPublic()) {
			chatMessage = ChatMessage.builder()
					.userFrom(principal.getName())
					.roomId(chatRoomId)
					.content(instantMessage.getContent())
					.addedTimestamp(new Timestamp(System.currentTimeMillis()))
					.isCustomer(true)
					.internal(false)
					.isPublic(true)
					.build();
			chatRoomService.sendPublicMessage(chatMessage);
		} else {
			chatMessage = ChatMessage.builder()
					.userFrom(principal.getName())
					.userTo(instantMessage.getUserTo())
					.roomId(chatRoomId)
					.content(instantMessage.getContent())
					.addedTimestamp(new Timestamp(System.currentTimeMillis()))
					.isCustomer(true)
					.internal(false)
					.build();
			chatRoomService.sendPrivateMessage(chatMessage);
		}
	}
}
