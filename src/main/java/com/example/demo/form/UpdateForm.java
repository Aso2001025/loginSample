package com.example.demo.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

//ユーザー情報更新用フォーム
@Data
public class UpdateForm {

	private MultipartFile icon;//画像用変数
	private String mail;
	private String user_name;
}
