package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class ProjectDonorDTO {
	private String donorName; 		// 기부한 유저 이름
	private String profileImage; 	// 기부한 유저 프로필 사진 경로
}
