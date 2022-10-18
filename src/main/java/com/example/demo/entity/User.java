package com.example.demo.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	private Integer user_id;
	private String user_name;
	private String icon;
	private String mail;
	private String pass;
	private Integer level;
	private Date birthday;
	private Date reg_date;
}