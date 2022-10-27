package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class InputForm {
	
	@Email
	private String mail;
	@NotBlank
	private String user_name;
	@Pattern(regexp="(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-z0-9]{6,}" )
	private String pass;
	private String comPass;
	
	

}
