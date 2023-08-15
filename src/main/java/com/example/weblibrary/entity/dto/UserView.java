package com.example.weblibrary.entity.dto;

import com.example.weblibrary.entity.User;

import java.util.List;



public class UserView {
	private int pageCount;
	List<User> users;

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public UserView(int pageCount, List<User> users) {
		super();
		this.pageCount = pageCount;
		this.users = users;
	}

}
