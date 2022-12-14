package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.User;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User,Integer>{
	//メールでユーザー検索
	List<User> findByMail(String mail);
	//メール更新
	@Modifying
	@Query("update User set mail = :mail where user_id = :id")
	void setMail(@Param("mail") String mail,@Param("id") Integer user_id);
	//ユーザーネーム更新
	@Modifying
	@Query("update User set user_name = :user_name where user_id = :id")
	void setName(@Param("user_name") String user_name,@Param("id") Integer user_id);
	//アイコン更新
	@Modifying
	@Query("update User set icon = :icon where user_id = :id")
	void setIcon(@Param("icon") String icon,@Param("id") Integer user_id);
	//パスワード更新
	@Modifying
	@Query("update User set pass = :pass where user_id = :id")
	void setPass(@Param("pass") String pass,@Param("id") Integer user_id);
}
