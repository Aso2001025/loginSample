package com.example.demo.service;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.User;
import com.example.demo.form.UpdateForm;

public interface UserService {
	List<User> findMail(String mail);
	
	Optional<User> findId(Integer user_id);
	
	Boolean match(List<User> list,String pass);
	
	void addUser(User user);
	
	String hash(String pass);
	
	Date getDate();
	
	UpdateForm appConfig();
}