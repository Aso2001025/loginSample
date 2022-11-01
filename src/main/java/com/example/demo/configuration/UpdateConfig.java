package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.form.UpdateForm;

@Configuration
public class UpdateConfig implements WebMvcConfigurer {
	@Autowired
	private UpdateForm updateForm;
	
	@Override //addResourceHandlersのオーバーライドをしJavaプログラムとして記述。「ソース→メソッドのオーバーライド→addResourceHandlersを選択」
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//ファイルの場所はURI形式で渡す必要があるため、FileクラスのtoURIメソッドを使用して変換
		String imageDirUri = updateForm.getMFile().toURI().toString();
		//「/images/以下のパスがリクエストされたら」「imageDirにある対応ファイルを使用する」という設定
		registry.addResourceHandler("/images/**").addResourceLocations(imageDirUri);
	}
}
