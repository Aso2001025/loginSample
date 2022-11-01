package com.example.demo.service;
import java.sql.Date;
import java.util.List;

import com.example.demo.entity.User;

public interface UserService {
	List<User> find(String mail);
	
	Boolean match(List<User> list,String pass);
	
	void addUser(User user);
	
	String hash(String pass);
	
	Date getDate();
}