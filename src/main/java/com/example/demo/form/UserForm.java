package com.example.demo.form;

import lombok.Data;

//ログイン用フォーム
 @Data
public class UserForm {
	private String mail;
	private String pass;
	private String user_name;
}