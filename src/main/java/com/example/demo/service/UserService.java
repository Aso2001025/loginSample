package com.example.demo.service;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.User;

public interface UserService {
	List<User> findMail(String mail);
	
	Optional<User> findId(Integer user_id);
	
	Boolean match(List<User> list,String pass);
	
	void addUser(User user);
	
	String hash(String pass);
	
	Date getDate();
	
	String uploadAction(MultipartFile multipartFile);
	
	
	void updateMail(String mail,Integer user_id);
	
	void updateName(String user_name,Integer user_id);
	
}