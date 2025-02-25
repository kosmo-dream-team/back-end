package com.dream_on.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dream_on.springboot.domain.ProjectEntity;
import com.dream_on.springboot.dto.AllProjectDTO;
import com.dream_on.springboot.dto.CategoryDTO;
import com.dream_on.springboot.dto.ProjectCommentDTO;
import com.dream_on.springboot.dto.ProjectDetailDTO;
import com.dream_on.springboot.dto.ProjectDonorDTO;
import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.dto.RecentProjectDTO;

@Mapper
public interface ProjectMapper {


    // 모든 프로젝트 정보 조회
    List<AllProjectDTO> findAllProjects();	


    /**
     * �듅�젙 移댄뀒怨좊━�뿉 ���븳 湲곕��븸 �긽�쐞 10�쐞 �봽濡쒖젥�듃
     * @param categoryId 議고쉶�븷 移댄뀒怨좊━ ID
     */
	List<ProjectSummaryDTO> findTop10ProjectsByDonationInCategory(@Param("categoryId") int categoryId);
        
    
    // 理쒓렐 �떊�꽕�맂 �봽濡쒖젥�듃 3媛� �젙蹂� 議고쉶
	List<RecentProjectDTO> findLatest3Projects();
    
    
    /*
	罹좏럹�씤(湲곕�-�긽�꽭蹂닿린)
	�빐�떦 �봽濡쒖젥�듃 媛� �쟾�떖 :
	�봽濡쒖젥�듃 紐낆묶, 移대뜲怨좊━, �떆�옉�씪, 留덇컧�씪, �꽕紐�,
	留덇컧�씪源뚯� �궓�� �씪�닔(�삁: D-16), 紐⑺몴湲덉븸, �룄�떖�쑉(�삁: 33%)
	�빐�떦 �봽濡쒖젥�듃�쓽 湲곕��옄    
	*/

    // 1) �봽濡쒖젥�듃 湲곕낯 �젙蹂� + sum(donation_amount)濡� �룄�떖�쑉 怨꾩궛
    ProjectDetailDTO findProjectDetail(@Param("projectId") Long projectId);

    // 2) �봽濡쒖젥�듃 移댄뀒怨좊━ 紐⑸줉
    List<CategoryDTO> findCategorysByProjectId(@Param("projectId") Long projectId);
    
    // 3) 湲곕��옄 紐⑸줉(�땳�꽕�엫 �벑)
    List<ProjectDonorDTO> findDonorsByProjectId(@Param("projectId") Long projectId);

    // 4) �봽濡쒖젥�듃 �뙎湲� 紐⑸줉
    List<ProjectCommentDTO> findCommentsByProjectId(@Param("projectId") Long projectId);
    
    // (異붽�) 湲곕� INSERT
    int insertDonation(@Param("userId") int userId,
                       @Param("projectId") Long projectId,
                       @Param("amount") int amount,
                       @Param("paymentMethod") String paymentMethod);
    
    // �봽濡쒖젥�듃 醫뗭븘�슂
    int updateLikeCount(@Param("projectId") Long projectId);
    
    // �봽濡쒖젥�듃 怨듭쑀
    int updateShareCount(@Param("projectId") Long projectId);

    // �봽濡쒖젥�듃 �뙎湲� 醫뗭븘�슂
    int updateLikeComment(@Param("commentId") Long commentId);
    
    // �깉濡쒖슫 �봽濡쒖젥�듃 �젅肄붾뱶瑜� DB�뿉 異붽�
    int insertProject(ProjectEntity project);

    // 湲곗〈�쓽 �봽濡쒖젥�듃 �젙蹂대�� �닔�젙
    int updateProject(ProjectEntity project);

    /*
	* �쟾泥� 移댄뀒怨좊━�뿉 ���븳 湲곕��븸 �긽�쐞 10�쐞 �봽濡쒖젥�듃
	�봽濡쒖젥�듃紐�, 移댄뀒怨좊━, 紐⑺몴�씪, 留덇컧�씪源뚯� �궓�� �씪�닔, 紐⑺몴湲덉븸, �룄�떖�쑉

    @Select("""
        SELECT 
            p.title        AS projectName,
            p.category            AS category,
            DATE_FORMAT(p.end_date, '%Y-%m-%d') AS goalDate,
            DATEDIFF(p.end_date, NOW()) AS daysLeft,
            p.target_amount       AS goalAmount,
            -- NULL 諛⑹�濡� SUM(d.donation_amount)�� p.target_amount瑜� 0 泥섎━
            CAST( ( COALESCE(SUM(d.donation_amount), 0) / p.target_amount ) * 100 AS UNSIGNED ) AS donationRate
            p.project_image                        AS projectImage
        FROM project p
        LEFT JOIN donation d 
               ON p.project_id = d.project_id
        GROUP BY p.project_id
        ORDER BY SUM(d.donation_amount) DESC
        LIMIT 10
        """)
	*/
	List<ProjectSummaryDTO> findTop10ProjectsByDonation();
    
	// 전체 프로젝트 조회
    List<ProjectEntity> findAll();
	
    // 프로젝트 ID로 단일 프로젝트 조회
    ProjectEntity findById(int projectId);
	
    // 프로젝트 업데이트 (승인/거절 등 상태 변경 포함)
    int update(ProjectEntity project);
	
    @Delete("DELETE FROM project WHERE project_id = #{projectId}")
    int deleteProject(@Param("projectId") int projectId);
}
