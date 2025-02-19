package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class DonationFeedbackBoardDTO {
	private int post_id;
	private int user_id;
	private String title;
	private String content;
	private java.sql.Date created_at;
	private java.sql.Date updated_at;
	private String is_active;
	private int project_id;
	private String image;
}