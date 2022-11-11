package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

//新規登録用フォーム
@Data
public class InputForm {
	
	@Email
	private String mail;
	@NotBlank
	private String user_name;
	@Pattern(regexp="(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-z0-9]{6,}" )
	private String pass;
	private String comPass;//確認用パスワード
	
	

}
