package com.dream_on.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.dream_on.springboot.dto.MonthlyDonationDTO;
import com.dream_on.springboot.dto.TopDonorDTO;

@Mapper
public interface StatisticsMapper {

    /**
     * 총 기부금 (donation_amount 합계)
     */
    @Select("""
        SELECT COALESCE(SUM(donation_amount), 0)
        FROM donation
    """)
    long getTotalDonationAmount();

    /**
     * 총 캠페인 수 (project 테이블의 row count)
     */
    @Select("""
        SELECT COUNT(*) FROM project
    """)
    int getTotalCampaignCount();

    /**
     * 총 기부 횟수 (donation 테이블 row count)
     * 만약 '기부 횟수'를 기부 건수(행 수)로 본다면 COUNT(*).
     */
    @Select("""
        SELECT COUNT(*) FROM donation
    """)
    int getTotalDonationCount();

    /**
     * 최근 12개월 월별 기부금 추이
     *   - year: 'YYYY-MM'
     *   - donation: 그 달의 기부금 합
     * 주의: DB마다 날짜 함수가 다를 수 있습니다.
     * MySQL 기준 예시
     */
    @Select("""
        SELECT DATE_FORMAT(donation_date, '%Y-%m') AS year,
               SUM(donation_amount) AS donation
        FROM donation
        WHERE donation_date >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH)
        GROUP BY DATE_FORMAT(donation_date, '%Y-%m')
        ORDER BY year DESC
        LIMIT 12
    """)
    List<MonthlyDonationDTO> getMonthlyDonationChange();

    /**
     * 기부 많이 한 사람 순위
     *   - user_id: 사용자 이름(또는 닉네임)
     *   - total_donation_count: 총 기부액
     * 예: 상위 5명만 뽑는다면 LIMIT 5
     * (원하는 만큼 조정)
     */
    @Select("""
        SELECT u.user_name AS user_id,
               SUM(d.donation_amount) AS total_donation_count
        FROM donation d
        JOIN user u ON d.user_id = u.user_id
        GROUP BY d.user_id
        ORDER BY SUM(d.donation_amount) DESC
        LIMIT 3
    """)
    List<TopDonorDTO> getTopDonorRanking();

    /**
     * 전체 유저 수 (평균 기부금 계산에 필요할 수도 있음)
     *  - active 유저만 셀지, 전체 유저를 셀지 정책에 따라 달라짐
     */
    @Select("""
        SELECT COUNT(*) FROM user
    """)
    int getTotalUserCount();
}
