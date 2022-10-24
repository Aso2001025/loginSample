package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.form.InputForm;

@Component
public class PassValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO 自動生成されたメソッド・スタブ
		return InputForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO 自動生成されたメソッド・スタブ
		InputForm form = (InputForm) target;
		if(!form.getPass().equals(form.getComPass())) {
			errors.reject("com.example.demo.validator.InputValidator.message");
		}
	}

}
