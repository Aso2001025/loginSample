package com.example.demo.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UpdateForm {

	private MultipartFile icon;
	private String mail;
	private String user_name;
}
