package com.example.demowebsocket.domain;

import com.example.demowebsocket.utils.SystemUsers;

public class InstantMessageBuilder {

	private ChatMessage instantMessage;
	private InstantMessageChatRoom instantMessageChatRoom;
	private InstantMessageType instantMessageType;
	private InstantMessageText instantMessageText;
	private InstantMessageFromUser instantMessageFromUser;
	private InstantMessageToUser instantMessageToUser;

	public InstantMessageBuilder() {

	}

	public InstantMessageChatRoom newMessage() {
		instantMessage = new ChatMessage();
		instantMessageChatRoom = new InstantMessageChatRoom();
		return instantMessageChatRoom;
	}

	public class InstantMessageChatRoom {
		public InstantMessageType withChatRoomId(Long chatRoomId) {
			instantMessage.setRoomId(chatRoomId);
			instantMessageType = new InstantMessageType();
			return instantMessageType;
		}
	}

	public class InstantMessageType {
		public InstantMessageText systemMessage(){
			instantMessage.setUserFrom(SystemUsers.ADMIN.getUsername());
			instantMessage.setIsPublic(true);
			instantMessageText = new InstantMessageText();
			return instantMessageText;
		}

		public InstantMessageFromUser publicMessage() {
			instantMessage.setUserTo(null);
			instantMessageFromUser = new InstantMessageFromUser();
			return instantMessageFromUser;
		}

		public InstantMessageToUser privateMessage(){
			instantMessageToUser = new InstantMessageToUser();
			return instantMessageToUser;
		}
	}

	public class InstantMessageToUser {
		public InstantMessageFromUser toUser(String username) {
			instantMessage.setUserTo(username);
			instantMessageFromUser = new InstantMessageFromUser();
			return instantMessageFromUser;
		}
	}

	public class InstantMessageFromUser {
		public InstantMessageText fromUser(String username) {
			instantMessage.setUserFrom(username);
			instantMessageText = new InstantMessageText();
			return instantMessageText;
		}
	}

	public class InstantMessageText {
		public ChatMessage withText(String text) {
			instantMessage.setContent(text);
			return instantMessage;
		}
	}
}
