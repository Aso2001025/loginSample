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
import org.springframework.web.bind.support.SessionStatus;

import com.example.demo.entity.User;
import com.example.demo.form.InputForm;
import com.example.demo.form.PassForm;
import com.example.demo.form.UpdateForm;
import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;
import com.example.demo.validator.MailValidator;
import com.example.demo.validator.PassValidator;

@Controller
public class LoginController {
	
	//Form初期設定エリア
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
	
	@ModelAttribute
	public PassForm PassSetUpForm() {
		return new PassForm();
	}
	
	//sessionnを使えるようにする
	@Autowired
	  HttpSession session;
	//バリデーションを使えるようにする
	@Autowired
	PassValidator passValidator;
	@Autowired
	MailValidator mailValidator;
	//サービスを使えるようにする
	@Autowired
	private UserService service;
	
	//自作バリデーションの導入
	@InitBinder("inputForm")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(passValidator);
		webDataBinder.addValidators(mailValidator);
	}
	
	
	
	//ログイン画面への遷移
	@GetMapping("index")
	public String indexView() {
		return "index";
	}
	
	//ログイン処理
	@PostMapping(value="login",params="com")
	public String login(@Validated UserForm f,BindingResult bindingResult,Model model) {
		//バリデーション
		if(bindingResult.hasErrors()) {
			return "index";
		}
		//メールで検索
		List<User> list = service.findMail(f.getMail());
		//メールとパスワードが正しいとき
		if(!list.isEmpty() && service.match(list.get(0).getPass(), f.getPass())) {
			//sessionnに値を登録
			session.setAttribute("user_id", list.get(0).getUser_id());
			session.setAttribute("user_name", list.get(0).getUser_name());
			return "login-ok";
		}else {
			return "login-ng";
		}
		
		
	}
	
	//新規登録画面へ遷移
	@PostMapping(value="login",params="new")
	public String newInputView() {
		return "new-input";
	}
	
	//新規登録処理
	@PostMapping("new")
	public String newConfirmView(@Validated InputForm f,BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			return "new-input";
		}
		//パスワードのハッシュ化
		String hash = service.hash(f.getPass());
		//現在の日付を取得
		Date date = service.getDate();
		//登録用のエンティティの定義
		User user = new User(null,f.getUser_name(),null,f.getMail(),hash,date);
		//登録処理
		service.addUser(user);
		return "new-confirm";
	}
	
	//アカウント詳細へ遷移
	@PostMapping("acount")
	public String acountViwe(Model model) {
		//sessionnに登録されているユーザー情報を取得
		Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		model.addAttribute("user",user.get());
		
		return "acount";
	}
	
	//アイコンの更新画面へ遷移
	@PostMapping(value="update",params="icon")
	 public String updateIconView(Model model) {
		//sessionnに登録されているユーザー情報を取得
		Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		model.addAttribute(user.get());
		 return "updateIcon";
	 }
	
	 //メールの更新画面へ遷移
	 @PostMapping(value="update",params="mail")
	 public String updateMailView(Model model) {
		//sessionnに登録されているユーザー情報を取得
		Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		model.addAttribute(user.get());
		return "updateMail";
	 }
	 
	 //ユーザーネームの更新画面へ遷移
	 @PostMapping(value="update",params="user_name")
	 public String updateNameView(Model model) {
		//sessionnに登録されているユーザー情報を取得
		Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		model.addAttribute(user.get());
		return "updateName";
	 }
	 
	 //パスワードの更新画面へ遷移
	 @PostMapping(value="update",params="pass")
	 public String UpdatePassView() {
		 return "updatePass";
	 }
	 //ログアウト処理
	 @PostMapping(value="update",params="logout")
	 public String logput(SessionStatus sessionStatus) {
		 //sessionに登録さてている情報を削除
		 session.removeAttribute("user_id");
		 session.removeAttribute("user_name");
		 session.invalidate();
		 //sessionのリセット
		 sessionStatus.setComplete();
		 return "index";
	 }
	 
	 
	 //メール更新処理
	 @PostMapping("updateMail")
	 public String updateMail(UpdateForm f,Model model) {
		 //更新メソッドへ
		 service.updateMail(f.getMail(),(Integer)session.getAttribute("user_id"));
		//sessionnに登録されているユーザー情報を取得
		 Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		 model.addAttribute("user",user.get());
		 return "acount";
	 }
	 
	 //ユーザーネーム更新処理
	 @PostMapping("updateName")
	 public String updateName(UpdateForm f,Model model) {
		 //更新メソッドへ
		 service.updateName(f.getUser_name(),(Integer)session.getAttribute("user_id"));
		//sessionnに登録されているユーザー情報を取得
		 Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		 model.addAttribute("user",user.get());
		 
		 return "acount";
	 }
	 
	 
	 //アイコン更新処理
	 @PostMapping("updateIcon")
	 public String updateIcon(UpdateForm f,Model model) {
		 //画像アップロードメソッドへ
		 String path = service.uploadAction(f.getIcon());
		 //更新メソッドへ
		 service.updateIcon(path,(Integer)session.getAttribute("user_id"));
		//sessionnに登録されているユーザー情報を取得
		 Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
		 model.addAttribute("user",user.get());
		 
		 return "acount";
	 }
	 
	 //パスワード更新処理
	 @PostMapping("updatePass")
	 public String updateIcon(PassForm f,Model model) {
		 //現在のパスワード取得
		Optional<User> list = service.findId((Integer) session.getAttribute("user_id"));
		//入力されたパスワードが正しい場合
		if(service.match(list.get().getPass(), f.getOldPass())) {
			//パスワードのハッシュ化
			String hash = service.hash(f.getNewPass());
			//更新画面へ
			service.updatePass(hash,(Integer)session.getAttribute("user_id"));
			//sessionnに登録されているユーザー情報を取得
			Optional<User> user = service.findId((Integer) session.getAttribute("user_id"));
			model.addAttribute("user",user.get());
			return "acount";
		}else {
			//パスワードが間違ってた場合
			model.addAttribute("msg","現在のパスワードが違います");
			return "updatePass";
		}
		 
	 }
	 
	
	
}