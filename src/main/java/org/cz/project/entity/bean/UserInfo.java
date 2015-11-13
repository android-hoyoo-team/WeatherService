package org.cz.project.entity.bean;

public class UserInfo<T> {
	private String type;
	public UserInfo(String type, T users) {
		super();
		this.type = type;
		this.users = users;
	}
	private T users;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public T getUsers() {
		return users;
	}
	public void setUsers(T users) {
		this.users = users;
	}
	

}
