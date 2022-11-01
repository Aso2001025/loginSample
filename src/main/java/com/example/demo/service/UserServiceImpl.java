package com.example.demo.service;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository repository;
	
	 @Autowired
	  HttpSession session; 
	
	@Override
	public List<User> find(String mail) {
		return repository.findByMail(mail);
	}
	
	@Override
	public Boolean match(List<User> list,String pass) {
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		
		if (bcpe.matches(pass, list.get(0).getPass())) {
			session.setAttribute("user_id", list.get(0).getUser_id());
			session.setAttribute("user_name", list.get(0).getUser_name());
			return true;
		}
		return false;
	}
	
	@Override
	public void addUser(User user) {
		repository.save(user);
	}
	
	@Override
	public String hash(String pass) {
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		String hash = bcpe.encode(pass);
		return hash;
	}
	
	@Override
	public Date getDate() {
		Date date = new Date(new java.util.Date().getTime());
		
		return date;
		
	}
}