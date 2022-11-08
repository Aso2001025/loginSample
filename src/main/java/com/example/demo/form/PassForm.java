package com.example.demo.form;

import javax.validation.constraints.Pattern;

import lombok.Data;


//パスワード更新用フォーム
@Data
public class PassForm {
	private String oldPass;//現在のパスワード
	@Pattern(regexp="(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-z0-9]{6,}" )
	private String newPass;//新しいパスワード
	private String comPass;//確認用パスワード
}
