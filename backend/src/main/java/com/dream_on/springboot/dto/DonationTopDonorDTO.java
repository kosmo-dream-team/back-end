package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class DonationTopDonorDTO {
    private int userId;
    private String userName;
    private int totalDonation;
}
