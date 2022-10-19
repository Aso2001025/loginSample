package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.User;

public interface UserService {
	Boolean find(String mail,String pass);
}