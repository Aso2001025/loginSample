package com.example.demo.form;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class UserForm {
	@Email
	private String mail;
	@Length(min=3, max=10,message="パスワードは3文字以上１０字以下です")
	private String pass;
	
	private String user_name;
}