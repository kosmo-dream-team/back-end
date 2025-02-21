package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class CommentDTO {
	private int projectId;
	private int userId;
	private String comment;
}