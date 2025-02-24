package com.dream_on.springboot.dto;

import java.util.List;
import lombok.Data;

@Data
public class DashboardDTO {
    private int totalUsers;
    private int totalProjects;
    private int totalDonations;
    private double avgDonationPerUser;
    private List<DonationTrendDTO> donationTrends;
    private List<DonationTopDonorDTO> topDonors;
}
