package com.example.demo.service;

import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.form.UpdateForm;
import com.example.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository repository;
	
	  
	
	@Override
	public List<User> findMail(String mail) {
		return repository.findByMail(mail);
	}
	
	@Override
	public Optional<User> findId(Integer user_id) {
		return repository.findById(user_id);
	}
	
	@Override
	public Boolean match(List<User> list,String pass) {
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		
		if (bcpe.matches(pass, list.get(0).getPass())) {
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
	
	@Bean//@Beanアノテーションを付けたメソッドを定義しておくことでSpring Frameworkがオブジェクトを登録して後で使える
	public UpdateForm appConfig() {
		
		// 起動時のディレクトリをAppConfigのimageDirフィールドに保持しておく
		File imageDir = new File("images");
		imageDir = imageDir.getAbsoluteFile();
		
		// imagesフォルダがなかったら作成する
		if(!imageDir.exists()) {
			imageDir.mkdir();
		}
		UpdateForm appConfig = new UpdateForm();
		appConfig.setMFile(imageDir);
		return appConfig;
	}
}