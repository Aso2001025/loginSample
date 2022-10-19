package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository repository;
	
	@Override
	public Boolean find(String mail,String pass) {
		List<User> list = repository.findByMail(mail);
	}
}