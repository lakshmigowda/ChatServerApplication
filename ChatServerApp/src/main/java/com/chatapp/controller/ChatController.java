package com.chatapp.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
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

				dout.writeUTF("Welcome to the Chat Server!" + "\n"
						+ "Loging Name?");
				String command = din.readUTF();

				// get username
				while (ChatManager.isUserExists(command)) {
					dout.writeUTF("Sorry, name taken." + "\n" + "Loging Name?");
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
						StringBuffer roomsBuffer = new StringBuffer();
						while (roomsIterator.hasNext()) {
							Chatroom tempRoom = roomsIterator.next();
							roomsBuffer.append("* " + tempRoom.getName() + " ("
									+ tempRoom.getUsers().size() + ")" + "\n");
						}
						roomsBuffer.append("end of list.");
						dout.writeUTF(roomsBuffer.toString());
						command = din.readUTF();
					}

					case "/join": {
						roomName = command.split(" ")[1];
						dout.writeUTF("entering room: " + roomName);
						Set<User> users = ChatManager
								.getUserListForChatroom(roomName);
						Iterator<User> usersIterator = users.iterator();
						StringBuffer usersBuffer = new StringBuffer();
						while (usersIterator.hasNext()) {
							User tempUser = usersIterator.next();
							usersBuffer
									.append("* " + tempUser.getName() + "\n");
						}
						usersBuffer.append("end of list.");
						dout.writeUTF(usersBuffer.toString());

						ChatManager.addChatroomMessage(
								"* new user joined chat: " + userName,
								userName, roomName); // need to fix this
						Chatroom chatroom = ChatManager.getChatroom(roomName);
						Set<Message> messages = chatroom.getMessages();
						Iterator<Message> chatroomMessagesIterator = messages
								.iterator();
						StringBuffer messagesBuffer = new StringBuffer();
						while (chatroomMessagesIterator.hasNext()) {
							Message message = chatroomMessagesIterator.next();
							if (message.getUser().getName().equals(userName)) {
								messagesBuffer.append(message.getText() + "\n");
							}
							messagesBuffer.append(message.getUser() + " : "
									+ message.getText());
						}
						dout.writeUTF(usersBuffer.toString());
						count = messages.size();

						command = din.readUTF();

						while (!command.equals("/leave")) {
							ChatManager.addChatroomMessage(command, userName,
									roomName); // need to fix this
							chatroom = ChatManager.getChatroom(roomName);
							messages = chatroom.getMessages();
							chatroomMessagesIterator = messages.iterator();
							messagesBuffer = new StringBuffer();
							int tempCount = 0;
							while (chatroomMessagesIterator.hasNext()) {
								if (tempCount > count) {
									Message message = chatroomMessagesIterator
											.next();
									if (message.getUser().getName()
											.equals(userName)) {
										messagesBuffer.append(message.getText()
												+ "\n");
									}
									messagesBuffer.append(message.getUser()
											+ " : " + message.getText() + "\n");
								} else {
									tempCount++;
								}
							}
							dout.writeUTF(usersBuffer.toString());
							count = messages.size();
							command = din.readUTF();
						}

						ChatManager.addChatroomMessage("* user has left chat:"
								+ userName, userName, roomName); // need to fix this
						chatroom = ChatManager.getChatroom(roomName);
						messages = chatroom.getMessages();
						chatroomMessagesIterator = messages.iterator();
						messagesBuffer = new StringBuffer();
						int tempCount = 0;
						while (chatroomMessagesIterator.hasNext()) {
							if (tempCount > count) {
								Message message = chatroomMessagesIterator
										.next();
								if (message.getUser().getName()
										.equals(userName)) {
									messagesBuffer.append(message.getText()
											+ "\n");
								}
								messagesBuffer.append(message.getUser() + " : "
										+ message.getText() + "\n");
							} else {
								tempCount++;
							}
						}
						dout.writeUTF(usersBuffer.toString());
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
