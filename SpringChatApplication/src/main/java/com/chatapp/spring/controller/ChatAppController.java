package com.chatapp.spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chatapp.entity.Chatroom;
import com.chatapp.manager.ChatManager;

@Controller
public class ChatAppController {

	//List chatrooms
	@RequestMapping(value = "/home")
	public ModelAndView handleHome() {
		ModelAndView model = new ModelAndView("home");
		List<Chatroom> rooms = ChatManager.getAllChatrooms();
		model.addObject("rooms", rooms);
		return model;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getChatroom")
	public Chatroom handleGetChatroom(@RequestParam String chatroomName) {
		Chatroom chatroom = ChatManager.getChatroom(chatroomName);
		return chatroom;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/createUser")
	public String handleCreateUser(@RequestParam String userName) {
		Boolean result = ChatManager.isUserExists(userName);
		if (result) {
			return "error";
		} else {
			ChatManager.addUser(userName);
			return "success";
		}
	}

	@RequestMapping(value = "/createMessage")
	public Chatroom handleCreateMessage(@ModelAttribute String message,
			String userName, String chatroomName) {
		Chatroom chatroom = ChatManager.addChatroomMessage(message, userName,
				chatroomName);
		return chatroom;
	}

}