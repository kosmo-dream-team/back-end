package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class ProjectCommentDTO {
	private String profileImage;	// 댓글 작성자의 프로필 이미지
	private String userName;		// 댓글 작성자의 이름
	private String comment;			// 댓글 내용
	private int likeCount;			// 댓글 좋아요 수
	private String postDate;		// 댓글 작성 일자
}