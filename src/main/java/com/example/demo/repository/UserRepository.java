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
	List<User> findByMail(String mail);
	@Modifying
	@Query("update User set mail = :mail where user_id = :id")
	void setMail(@Param("mail") String mail,@Param("id") Integer user_id);
	@Modifying
	@Query("update User set user_name = :user_name where user_id = :id")
	void setName(@Param("user_name") String user_name,@Param("id") Integer user_id);
}
