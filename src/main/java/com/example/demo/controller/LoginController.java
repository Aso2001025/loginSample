package com.example.demo.controller;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.User;
import com.example.demo.form.InputForm;
import com.example.demo.form.UpdateForm;
import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;
import com.example.demo.validator.MailValidator;
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
	  HttpSession session;
	
	@Autowired
	PassValidator passValidator;
	@Autowired
	MailValidator mailValidator;
	@Autowired
	UpdateForm updateForm;
	
	@InitBinder("inputForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(passValidator);
		webDataBinder.addValidators(mailValidator);
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
		List<User> list = service.findMail(f.getMail());
		if(service.match(list, f.getPass())) {
			session.setAttribute("user_id", list.get(0).getUser_id());
			session.setAttribute("user_name", list.get(0).getUser_name());
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
	
	@PostMapping("acount")
	public String acountViwe(Model model) {
		Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		model.addAttribute("user",user.get());
		
		return "acount";
	}
	
	@PostMapping(value="setting",params="update")
	public String upload(@RequestParam MultipartFile file, Model model) {
		if(file.isEmpty()) {
			model.addAttribute("error", "ファイルを指定してください");
			return "index";
		}
		File dest = new File(updateForm.getMFile(),"picture.jpg");
		try {
			file.transferTo(dest); //表示される修正候補の「try/catchで囲む」を選択
		} catch (IllegalStateException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return "acount";
		
	}
	
	
}