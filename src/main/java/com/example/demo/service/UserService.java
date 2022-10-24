package com.example.demo.service;
import java.sql.Date;

import com.example.demo.entity.User;

public interface UserService {
	Boolean find(String mail,String pass);
	
	void addUser(User user);
	
	String hash(String pass);
	
	Date getDate();
}