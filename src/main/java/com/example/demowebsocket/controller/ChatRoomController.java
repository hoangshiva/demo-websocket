package com.example.demowebsocket.controller;


import com.example.demowebsocket.domain.ChatRoomUser;
import com.example.demowebsocket.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatRoomController {

	@Autowired
	private ChatRoomService chatRoomService;


	@RequestMapping("/chatroom/{chatRoomId}")
	public ModelAndView join(@PathVariable String chatRoomId, Principal principal) {
		ModelAndView modelAndView = new ModelAndView("chatroom");
		modelAndView.addObject("chatRoom", chatRoomService.findById(chatRoomId));
		return modelAndView;
	}

	@SubscribeMapping("/connected.users")
	public List<ChatRoomUser> listChatRoomConnectedUsersOnSubscribe(SimpMessageHeaderAccessor headerAccessor) {
		String chatRoomId = headerAccessor.getSessionAttributes().get("chatRoomId").toString();
		return chatRoomService.findById(chatRoomId).getConnectedUsers();
	}

	@SubscribeMapping("/old.messages")
	public List<InstantMessage> listOldMessagesFromUserOnSubscribe(Principal principal,
			SimpMessageHeaderAccessor headerAccessor) {
		String chatRoomId = headerAccessor.getSessionAttributes().get("chatRoomId").toString();
		return instantMessageService.findAllInstantMessagesFor(principal.getName(), chatRoomId);
	}

	@MessageMapping("/send.message")
	public void sendMessage(@Payload InstantMessage instantMessage, Principal principal,
			SimpMessageHeaderAccessor headerAccessor) {
		String chatRoomId = headerAccessor.getSessionAttributes().get("chatRoomId").toString();
		instantMessage.setFromUser(principal.getName());
		instantMessage.setChatRoomId(chatRoomId);

		if (instantMessage.isPublic()) {
			chatRoomService.sendPublicMessage(instantMessage);
		} else {
			chatRoomService.sendPrivateMessage(instantMessage);
		}
	}
}
