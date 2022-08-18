package com.example.demowebsocket.utils;


import com.example.demowebsocket.domain.ChatMessage;
import com.example.demowebsocket.domain.InstantMessageBuilder;

public class SystemMessages {

	public static final ChatMessage welcome(Long chatRoomId, String username) {
		return new InstantMessageBuilder()
				.newMessage()
				.withChatRoomId(chatRoomId)
				.systemMessage()
				.withText(username + " joined us :)");
	}

	public static final ChatMessage goodbye(Long chatRoomId, String username) {
		return new InstantMessageBuilder()
				.newMessage()
				.withChatRoomId(chatRoomId)
				.systemMessage()
				.withText(username + " left us :(");
	}
}
