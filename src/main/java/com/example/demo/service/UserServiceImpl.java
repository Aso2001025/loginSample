package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.User;
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
	
	@Override
	public String uploadAction(MultipartFile multipartFile) {
		
		String fileName = multipartFile.getOriginalFilename();
		
		Path filePath = Paths.get("/Users/maedasoukuu/DeskTop/java/system_2022/loginSample/img/" + fileName);
		
		try {
            //アップロードファイルをバイト値に変換
           // byte[] bytes  = multipartFile.getBytes();

            //バイト値を書き込む為のファイルを作成して指定したパスに格納
//            OutputStream stream = Files.newOutputStream(filePath);
            Files.copy(multipartFile.getInputStream(), filePath);
            //ファイルに書き込み
            //stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return "../../../../img/" + fileName;

	}
	
	
	
	@Override
	public void updateMail(String mail,Integer user_id) {
		repository.setMail(mail,user_id);
	}
	
	@Override
	public void updateName(String user_name,Integer user_id) {
		repository.setName(user_name,user_id);
	}
	
	@Override
	public void updateIcon(String path,Integer user_id) {
		repository.setIcon(path,user_id);
		
	}
}