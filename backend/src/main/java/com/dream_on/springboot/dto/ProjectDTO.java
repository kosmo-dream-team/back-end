package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class ProjectDTO {
	private int project_id;
	private String title;
	private String category;
	private int user_id;
	private int target_amount;
	private java.sql.Date start_date;
	private java.sql.Date end_date;
	private String status;
	private String description;
	private java.sql.Date created_at;
	private java.sql.Date updated_at;
	private java.sql.Date approval_date;
}