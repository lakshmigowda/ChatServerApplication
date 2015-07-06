package com.chatapp.manager;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.chatapp.dao.ChatappDAO;
import com.chatapp.entity.Chatroom;
import com.chatapp.entity.Message;
import com.chatapp.entity.User;

public class ChatManager {

	private static ChatappDAO chatroomHome = new ChatappDAO();

	public static List<Chatroom> getAllChatrooms() {
		List<Chatroom> rooms = chatroomHome.getAllChatrooms();
		return rooms;
	}

	public static Set<User> getUserListForChatroom(String name) {
		Set<User> rooms = null;
		Chatroom chatroom = chatroomHome.getChatroom(new Chatroom(name));
		if (chatroom != null) {
			rooms = chatroom.getUsers();
		}
		return rooms;
	}

	public static Set<Message> getMessageListForChatroom(Chatroom room) {
		Set<Message> messages = null;
		Chatroom chatroom = chatroomHome.getChatroom(room);
		if (chatroom != null) {
			messages = chatroom.getMessages();
		}
		return messages;
	}

	public static List<User> getAllUsers() {
		List<User> users = chatroomHome.getAllUsers();
		return users;
	}

	public static Chatroom getChatroom(String roomName) {
		Chatroom chatroom = chatroomHome.getChatroom(new Chatroom(roomName));
		return chatroom;
	}

	public static int addUser(String userName) {
		int userID = chatroomHome.addUser(new User(userName));
		return userID;
	}

	public static int addChatroom(String chatroomName) {
		int chatroomID = chatroomHome.addChatroom(new Chatroom(chatroomName));
		return chatroomID;
	}

	public static Chatroom addUserToChatroom(String userName,
			String chatroomName) {
		Chatroom room = getChatroom(chatroomName);
		room = chatroomHome.addUserToChatroom(new User(userName), room);
		return room;
	}

	public static Chatroom deleteUserFromChatroom(String userName,
			String chatroomName) {
		Chatroom room = getChatroom(chatroomName);
		room = chatroomHome.deleteUserFromChatroom(new User(userName), room);
		return room;
	}

	public static String deleteUser(String userName) {
		chatroomHome.deleteUser(new User(userName));
		return "success";
	}

	public static Chatroom addChatroomMessage(String text, String userName,
			String chatroomName) {
		chatroomHome.addChatroomMessage(new Message(new Chatroom(chatroomName),
				getUser(userName), text));
		return getChatroom(chatroomName);
	}

	public static User getUser(String userName) {
		User user = chatroomHome.getUser(new User(userName));
		return user;
	}

	public static boolean isUserExists(String name) {
		List<User> users = getAllUsers();
		Iterator<User> usersIterator = users.iterator();
		while (usersIterator.hasNext()) {
			if (usersIterator.next().getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
