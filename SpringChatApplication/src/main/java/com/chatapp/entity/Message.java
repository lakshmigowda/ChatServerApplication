package com.chatapp.entity;

// default package
// Generated Jul 5, 2015 7:10:39 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

/**
 * Message generated by hbm2java
 */
@Entity
@Table(name = "message", catalog = "chatapp")
public class Message implements java.io.Serializable {

	private Integer id;
	@JsonBackReference
	private Chatroom chatroom;
	@JsonBackReference
	private User user;
	private String text;

	public Message() {
	}

	public Message(Chatroom chatroom, User user) {
		this.chatroom = chatroom;
		this.user = user;
	}

	public Message(Chatroom chatroom, User user, String text) {
		this.chatroom = chatroom;
		this.user = user;
		this.text = text;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cid", nullable = false)
	public Chatroom getChatroom() {
		return this.chatroom;
	}

	public void setChatroom(Chatroom chatroom) {
		this.chatroom = chatroom;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "uid", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "text", length = 100)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
