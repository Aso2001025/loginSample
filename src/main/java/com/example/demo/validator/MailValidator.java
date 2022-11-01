package com.example.demo.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.entity.User;
import com.example.demo.form.InputForm;
import com.example.demo.service.UserService;

@Component
public class MailValidator implements Validator {
	@Autowired
	UserService service;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO 自動生成されたメソッド・スタブ
		return InputForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO 自動生成されたメソッド・スタブ
		InputForm form = (InputForm) target;
		List<User> list = service.findMail(form.getMail());
		if(!list.isEmpty()) {
			errors.reject("com.example.demo.validator.MailValidator.message");
		}
	}

}
