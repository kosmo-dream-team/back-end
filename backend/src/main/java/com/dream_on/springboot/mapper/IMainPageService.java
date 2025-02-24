package com.dream_on.springboot.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.dream_on.springboot.domain.CategoryEntity;
import com.dream_on.springboot.dto.DonationFeedbackBoardDTO;
import com.dream_on.springboot.dto.ProjectDTO;
import com.dream_on.springboot.dto.UserDTO;

@Mapper
public interface IMainPageService {
	public int project_count(); // 캠페인 수 확인
	public Map<String, Integer> total_donation(); // 총 기부금, 기부 횟수 확인
	public Map<String, Object> monthly_donation(); // 월 별 기부금(최근 12개월)
	public Map<String, Integer> my_statistics(int user_id); // 나의 보유 금액, 나의 총 후원 금액 확인
	public boolean is_valid_user(String email, String password_hash); // 이메일, 비밀번호 일치 여부 확인
	public int create_user(UserDTO userDTO); // 회원 가입
	public ArrayList<ProjectDTO> project_category_list(String category); // 카테고리 별 프로젝트 목록
	public ArrayList<ProjectDTO> project_top3_by_donation(); // 기부금 순 프로젝트 Top3
	public ArrayList<ProjectDTO> project_top10_by_donation(); // 기부금 순 프로젝트 Top10
	public ArrayList<DonationFeedbackBoardDTO> get_recent_feedback(); // 후원 소감 최근 10개 확인
	public ArrayList<ProjectDTO> project_top3_by_start_date(); // 시작일 순 프로젝트 Top3
	 @Select("SELECT category_id, category FROM category")
	    List<CategoryEntity> findAll(); // 카테고리 목록 조회
}