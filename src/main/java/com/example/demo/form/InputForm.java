package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class InputForm {
	
	@Email
	@NotBlank
	private String mail;
	@NotBlank
	private String user_name;
	@Length(min=3, max=10)
	@NotBlank
	private String pass;
	@NotBlank
	private String comPass;
	
	

}
