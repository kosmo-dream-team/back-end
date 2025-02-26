package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class ProjectDonationDTO {
	private int userId;
	private int amount;
	private String paymentMethod;
}
