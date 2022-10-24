package com.example.demo.controller;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.form.InputForm;
import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;
import com.example.demo.validator.PassValidator;

@Controller
public class LoginController {
	
	@ModelAttribute
	public UserForm UserSetUpForm() {
		return new UserForm();
	}
	
	@ModelAttribute
	public InputForm InputSetUpForm() {
		return new InputForm();
	}
	
	@Autowired
	PassValidator passValidator;
	
	@InitBinder("inputForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(passValidator);
	}
	
	@Autowired
	private UserService service;
	
	@GetMapping("index")
	public String indexView() {
		return "index";
	}
	
	@PostMapping(value="login",params="com")
	public String login(@Validated UserForm f,BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			return "index";
		}
		if(service.find(f.getMail(), f.getPass())) {
			return "login-ok";
		}else {
			return "login-ng";
		}
		
		
	}
	
	@PostMapping(value="login",params="new")
	public String newInputView() {
		return "new-input";
	}
	
	@PostMapping("new")
	public String newConfirmView(@Validated InputForm f,BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			return "new-input";
		}
		String hash = service.hash(f.getPass());
		Date date = service.getDate();
		User user = new User(null,f.getUser_name(),null,f.getMail(),hash,0,null,date);

		service.addUser(user);
		return "new-confirm";
	}
	
	
}