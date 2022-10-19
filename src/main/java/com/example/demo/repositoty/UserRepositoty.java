package com.example.demo.repositoty;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.User;

public interface UserRepositoty extends CrudRepository<User,Integer>{

}
