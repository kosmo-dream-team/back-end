package com.dream_on.springboot.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.dream_on.springboot.dto.DashboardDTO;
import com.dream_on.springboot.dto.DonationTrendDTO;
import com.dream_on.springboot.dto.DonationTopDonorDTO;

@Mapper
public interface DashboardMapper {

    // 총 활성 회원 수 조회
    @Select("SELECT COUNT(*) FROM user WHERE is_active = 'Y'")
    int countUsers();

    // 전체 프로젝트 수 조회
    @Select("SELECT COUNT(*) FROM project")
    int countProjects();

    // 총 기부금 조회
    @Select("SELECT COALESCE(SUM(donation_amount), 0) FROM donation")
    int sumDonations();

    // 월별 기부 추이 조회 (예시: '2025-03' 형식)
    @Select({
        "SELECT DATE_FORMAT(donation_date, '%Y-%m') AS period,",
        "COALESCE(SUM(donation_amount), 0) AS totalDonation",
        "FROM donation",
        "GROUP BY period",
        "ORDER BY period"
    })
    List<DonationTrendDTO> getMonthlyDonationTrends();

    // 상위 기부자 조회 (상위 5명 예시)
    @Select("SELECT d.user_id AS userId, u.user_name AS userName, COALESCE(SUM(d.donation_amount), 0) AS totalDonation " +
            "FROM donation d " +
            "JOIN user u ON d.user_id = u.user_id " +
            "GROUP BY d.user_id, u.user_name " +
            "ORDER BY totalDonation DESC " +
            "LIMIT 5")
    List<DonationTopDonorDTO> getTopDonors();
}
