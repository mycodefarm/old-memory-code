package com.jimo.model;

public class User {
	private String id;
	private String username;
	private String password;
	private int fault_time;
	private String unlock_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getFault_time() {
		return fault_time;
	}

	public void setFault_time(int fault_time) {
		this.fault_time = fault_time;
	}

	public String getUnlock_time() {
		return unlock_time;
	}

	public void setUnlock_time(String unlock_time) {
		this.unlock_time = unlock_time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
