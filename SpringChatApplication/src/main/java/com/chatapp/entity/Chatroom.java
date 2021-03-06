package com.chatapp.entity;

// default package
// Generated Jul 5, 2015 7:10:39 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 * Chatroom generated by hbm2java
 */
@Entity
@Table(name = "chatroom", catalog = "chatapp")
public class Chatroom implements java.io.Serializable {

	private Integer id;
	private String name;
	@JsonManagedReference
	private Set<User> users = new HashSet<User>(0);
	@JsonManagedReference
	private Set<Message> messages = new HashSet<Message>(0);

	public Chatroom() {
	}

	public Chatroom(String name, Set<User> users, Set<Message> messages) {
		this.name = name;
		this.users = users;
		this.messages = messages;
	}

	public Chatroom(String name) {
		this.name = name;
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

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "userchatroom", catalog = "chatapp", joinColumns = { @JoinColumn(name = "cfid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ufid", nullable = false, updatable = false) })
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "chatroom")
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

}
