package com.chatapp.dao;

// default package
// Generated Jul 3, 2015 9:02:50 PM by Hibernate Tools 3.4.0.CR1

import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.chatapp.entity.Chatroom;
import com.chatapp.entity.Message;
import com.chatapp.entity.User;

/**
 * 
 * Home object for domain model class Chatroom.
 * 
 * @see .Chatroom
 * @author Hibernate Tools
 */
@Stateless
public class ChatappDAO {

	@PersistenceContext(unitName = "chatapp")
	private static SessionFactory factory;

	public List<Chatroom> getAllChatrooms() {

		List<Chatroom> chatrooms = null;
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			chatrooms = session.createQuery("FROM Chatroom").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return chatrooms;
	}

	public List<User> getAllUsers() {

		List<User> users = null;
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			users = session.createQuery("FROM User").list();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return users;
	}

	@SuppressWarnings({ "unchecked", "unused", "deprecation" })
	public Chatroom getChatroom(Chatroom room) {
		List<Chatroom> chatrooms = null;
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			chatrooms = session.createQuery(
					"FROM Chatroom C WHERE C.name = 'Chat'").list();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return chatrooms.get(0);
	}

	public Integer addUser(User userAdd) {
		int userId = 0;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			userId = (Integer) session.save(userAdd);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return userId;
	}

	public Integer addChatroom(Chatroom room) {
		int chatroomId = 0;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			chatroomId = (Integer) session.save(room);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return chatroomId;
	}

	public Chatroom addUserToChatroom(User newUser, Chatroom room) {
		Chatroom chatroom = null;
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			chatroom = (Chatroom) session
					.createQuery("FROM Chatroom WHERE name=" + room.getName());
			//add user to chatroom
			chatroom.getUsers().add(newUser);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return chatroom;

	}

	//delete user from a chat when the user quits the chat
	public Chatroom deleteUserFromChatroom(User user, Chatroom chatRoom) {
		Session session = factory.openSession();
		Chatroom chatroom = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			chatroom = (Chatroom) session.get(Chatroom.class,
					chatRoom.getName());
			Iterator<User> userIterator = chatroom.getUsers().iterator();
			while (userIterator.hasNext()) {
				User tempUser = userIterator.next();
				if (user.getName().equals(tempUser.getName())) {
					userIterator.remove();
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return chatRoom;
	}

	//delete user from users
	public void deleteUser(User userDelete) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User user = (User) session.get(User.class, userDelete.getId());
			session.delete(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	//create and add message to the chat
	public int addChatroomMessage(Message newMessage) {
		int messageId = 0;
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		Session session = factory.openSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			messageId = (Integer) session.save(newMessage);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return messageId;
	}

	public User getUser(User user) {
		User tempUser = null;
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			tempUser = (User) session.createQuery("FROM User WHERE name="
					+ user.getName());
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return tempUser;
	}
}
