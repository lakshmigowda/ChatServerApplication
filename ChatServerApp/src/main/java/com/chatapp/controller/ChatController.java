package com.chatapp.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.chatapp.entity.Chatroom;
import com.chatapp.entity.Message;
import com.chatapp.entity.User;
import com.chatapp.manager.ChatManager;

public class ChatController {
	public static void main(String args[]) throws Exception {
		Properties props = new Properties();
		InputStream in = ChatController.class
				.getResourceAsStream("server.properties");
		props.load(in);

		String port = props.getProperty("port");

		@SuppressWarnings("resource")
		ServerSocket soc = new ServerSocket(Integer.valueOf(port));
		System.out.println(" Server Started on Port Number:" + port);
		while (true) {
			System.out.println("Waiting for Connection ...");
			transferfile t = new transferfile(soc.accept());

		}
	}
}

class transferfile extends Thread {
	Socket ClientSoc;

	DataInputStream din;
	DataOutputStream dout;

	transferfile(Socket soc) {
		try {
			ClientSoc = soc;
			din = new DataInputStream(ClientSoc.getInputStream());
			dout = new DataOutputStream(ClientSoc.getOutputStream());
			System.out.println(" Client Connected ...");
			start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void run() {
		while (true) {
			try {
				String roomName = "";
				String userName = "";
				long count = 0;

				System.out.println("Waiting for Command ...");

				dout.writeUTF("Welcome to the Chat Server!");
				dout.writeUTF("Loging Name?");
				String command = din.readUTF();

				// get username
				while (ChatManager.isUserExists(command)) {
					dout.writeUTF("Sorry, name taken.");
					dout.writeUTF("Loging Name?");
					command = din.readUTF();
				}

				userName = command;
				dout.writeUTF("Welcome " + userName + "!");

				while (true) {
					command = din.readUTF();

					switch (command) {

					case "/rooms": {
						dout.writeUTF("Active rooms are:");
						List<Chatroom> rooms = ChatManager.getAllChatrooms();
						Iterator<Chatroom> roomsIterator = rooms.iterator();
						while (roomsIterator.hasNext()) {
							Chatroom tempRoom = roomsIterator.next();
							dout.writeUTF("* " + tempRoom.getName() + " ("
									+ tempRoom.getUsers().size() + ")");
						}
						dout.writeUTF("end of list.");
						command = din.readUTF();
					}

					case "/join": {
						roomName = command.split(" ")[1];
						dout.writeUTF("entering room: " + roomName);
						Set<User> users = ChatManager
								.getUserListForChatroom(roomName);
						Iterator<User> usersIterator = users.iterator();
						while (usersIterator.hasNext()) {
							User tempUser = usersIterator.next();
							dout.writeUTF("* " + tempUser.getName());
						}
						dout.writeUTF("end of list.");

						ChatManager.addChatroomMessage(
								"* new user joined chat: " + userName,
								userName, roomName); // need to fix this
						Chatroom chatroom = ChatManager.getChatroom(roomName);
						Set<Message> messages = chatroom.getMessages();
						Iterator<Message> chatroomMessagesIterator = messages
								.iterator();
						while (chatroomMessagesIterator.hasNext()) {
							Message message = chatroomMessagesIterator.next();
							if (message.getUser().getName().equals(userName)) {
								dout.writeUTF(message.getText());
							}
							dout.writeUTF(message.getUser() + " : "
									+ message.getText());
						}
						count = messages.size();

						command = din.readUTF();

						while (!command.equals("/leave")) {
							ChatManager.addChatroomMessage(command, userName,
									roomName); // need to fix this
							chatroom = ChatManager.getChatroom(roomName);
							messages = chatroom.getMessages();
							chatroomMessagesIterator = messages.iterator();
							int tempCount = 0;
							while (chatroomMessagesIterator.hasNext()) {
								if (tempCount > count) {
									Message message = chatroomMessagesIterator
											.next();
									if (message.getUser().getName()
											.equals(userName)) {
										dout.writeUTF(message.getText());
									}
									dout.writeUTF(message.getUser() + " : "
											+ message.getText());
								} else {
									tempCount++;
								}
							}
							count = messages.size();
							command = din.readUTF();
						}

						ChatManager.addChatroomMessage("* user has left chat:"
								+ userName, userName, roomName); // need to fix this
						chatroom = ChatManager.getChatroom(roomName);
						messages = chatroom.getMessages();
						chatroomMessagesIterator = messages.iterator();
						int tempCount = 0;
						while (chatroomMessagesIterator.hasNext()) {
							if (tempCount > count) {
								Message message = chatroomMessagesIterator
										.next();
								if (message.getUser().getName()
										.equals(userName)) {
									dout.writeUTF(message.getText());
								}
								dout.writeUTF(message.getUser() + " : "
										+ message.getText());
							} else {
								tempCount++;
							}
						}
						count = messages.size();
						command = din.readUTF();
					}

					case "/quit": {
						dout.writeUTF("BYE");
						this.suspend();
					}

					default: {
						dout.writeUTF("Please enter a proper command");
					}

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}
		}
	}
}
