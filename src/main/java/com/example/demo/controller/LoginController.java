package com.example.demo.controller;
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
	
	@ModelAttribute
	public UpdateForm UpdateSetUpForm() {
		return new UpdateForm();
	}
	
	@Autowired
	  HttpSession session;
	
	@Autowired
	PassValidator passValidator;
	@Autowired
	MailValidator mailValidator;
	
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
		if(!list.isEmpty() && service.match(list, f.getPass())) {
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
		User user = new User(null,f.getUser_name(),null,f.getMail(),hash,date);

		service.addUser(user);
		return "new-confirm";
	}
	
	@PostMapping("acount")
	public String acountViwe(Model model) {
		Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		model.addAttribute("user",user.get());
		
		return "acount";
	}
	
	
	@PostMapping(value="update",params="icon")
	 public String updateIconView(Model model) {
		Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		model.addAttribute(user.get());
		 return "updateIcon";
	 }
	
	 @PostMapping(value="update",params="mail")
	 public String updateMailView(Model model) {
		Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		model.addAttribute(user.get());
		return "updateMail";
	 }
	 
	 @PostMapping(value="update",params="user_name")
	 public String updateNameView(Model model) {
		Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		model.addAttribute(user.get());
		return "updateName";
	 }
	 
	 @PostMapping(value="update",params="logout")
	 public String logput() {
		
		 return "index";
	 }
	 
	 
	 
	 @PostMapping("updateMail")
	 public String updateMail(UpdateForm f,Model model) {
		 System.out.println(f.getMail());
		 service.updateMail(f.getMail(),(Integer)session.getAttribute("user_id"));
		 Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		 model.addAttribute("user",user.get());
		 return "acount";
	 }
	 
	 @PostMapping("updateName")
	 public String updateName(UpdateForm f,Model model) {
		 service.updateName(f.getUser_name(),(Integer)session.getAttribute("user_id"));
		 Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		 model.addAttribute("user",user.get());
		 
		 return "acount";
	 }
	 
	 
	 @PostMapping("updateIcon")
	 public String updateIcon(UpdateForm f,Model model) {
		 service.updateName(f.getUser_name(),(Integer)session.getAttribute("user_id"));
		 Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		 model.addAttribute("user",user.get());
		 
		 return "acount";
	 }
	 
	
	
}