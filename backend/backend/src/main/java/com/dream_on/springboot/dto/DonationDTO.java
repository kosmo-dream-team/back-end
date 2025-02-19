package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class DonationDTO {
	private int donation_id;
	private int user_id;
	private int project_id;
	private int donation_amount;
	private java.sql.Date donation_date;
	private String payment_method;
	private String status;
}