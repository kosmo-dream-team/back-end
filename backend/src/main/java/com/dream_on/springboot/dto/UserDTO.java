package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class UserDTO {
	private int user_id;
	private String email;
	private String password_hash;
	private String user_name;
	private String user_type;
	private int balance;
	private String rank;
	private java.sql.Date created_at;
	private java.sql.Date updated_at;
	private String is_active;
	private String profile_image;
}