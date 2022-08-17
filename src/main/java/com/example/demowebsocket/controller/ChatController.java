package com.example.demowebsocket.controller;

import com.example.demowebsocket.domain.ChatRoom;
import com.example.demowebsocket.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ChatController {
	
	@Autowired
	private ChatRoomService chatRoomService;

	
    @RequestMapping("/chat")
    public ModelAndView getRooms() {
    	ModelAndView modelAndView = new ModelAndView("chat");
    	List<ChatRoom> chatRooms = chatRoomService.list();
    	modelAndView.addObject("chatRooms", chatRooms);
    	return modelAndView;
    }
}
