package com.example.demo.service;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.User;

public interface UserService {
	//メールでユーザー検索
	List<User> findMail(String mail);
	//IDでユーザー検索
	Optional<User> findId(Integer user_id);
	//パスワードのチェック
	Boolean match(String inputPass,String pass);
	//ユーザー登録
	void addUser(User user);
	//ハッシュ化
	String hash(String pass);
	//日付取得
	Date getDate();
	//画像アップロード
	String uploadAction(MultipartFile multipartFile);
	
	//メール更新
	void updateMail(String mail,Integer user_id);
	//ユーザーネーム更新
	void updateName(String user_name,Integer user_id);
	//アイコン更新
	void updateIcon(String path,Integer user_id);
	//パスワード更新
	void updatePass(String pass,Integer user_id);
	
}