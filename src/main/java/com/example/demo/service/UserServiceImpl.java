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
	//リポジトリを使えるようにする
	@Autowired
	UserRepository repository;
	//メールでユーザー検索
	@Override
	public List<User> findMail(String mail) {
		return repository.findByMail(mail);
	}
	//IDでユーザー検索
	@Override
	public Optional<User> findId(Integer user_id) {
		return repository.findById(user_id);
	}
	//パスワードチェック
	@Override
	public Boolean match(String inputPass,String pass) {
		//ハッシュ化用のクラス
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		//パスワードがあっていた場合
		if (bcpe.matches(pass, inputPass) ){
			return true;
		}
		return false;
	}
	
	//ユーザーの登録
	@Override
	public void addUser(User user) {
		repository.save(user);
	}
	
	//ハッシュ化
	@Override
	public String hash(String pass) {
		//ハッシュ化用のクラス
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		//ハッシュ化
		String hash = bcpe.encode(pass);
		return hash;
	}
	
	//日付の取得
	@Override
	public Date getDate() {
		//日付の取得
		Date date = new Date(new java.util.Date().getTime());
		
		return date;
		
	}
	
	//画像のアップロード
	@Override
	public String uploadAction(MultipartFile multipartFile) {
		//ファイルの名前を取得
		String fileName = multipartFile.getOriginalFilename();
		//アップロード先のパスの指定
		Path filePath = Paths.get("/Users/maedasoukuu/DeskTop/java/system_2022/loginSample/img/" + fileName);
		
		try {
			//アップロード
            Files.copy(multipartFile.getInputStream(), filePath);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		//DBに登録する情報を返す
		return "../../../../img/" + fileName;

	}
	
	
	//メールの登録
	@Override
	public void updateMail(String mail,Integer user_id) {
		repository.setMail(mail,user_id);
	}
	//ユーザーネームの登録
	@Override
	public void updateName(String user_name,Integer user_id) {
		repository.setName(user_name,user_id);
	}
	//アイコンの登録
	@Override
	public void updateIcon(String path,Integer user_id) {
		repository.setIcon(path,user_id);
	}
	//パスワードの登録
	@Override
	public void updatePass(String pass,Integer user_id) {
		repository.setPass(pass,user_id);
	}
}