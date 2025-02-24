package com.dream_on.springboot.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dream_on.springboot.domain.ProjectEntity;
import com.dream_on.springboot.dto.CategoryDTO;
import com.dream_on.springboot.dto.ProjectCommentDTO;
import com.dream_on.springboot.dto.ProjectDetailDTO;
import com.dream_on.springboot.dto.ProjectDonorDTO;
import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.dto.RecentProjectDTO;
import com.dream_on.springboot.mapper.ProjectMapper;

import lombok.RequiredArgsConstructor;

/**
 * ProjectService
 *
 * <p>�봽濡쒖젥�듃�� 愿��젴�맂 �쟾諛섏쟻�씤 鍮꾩쫰�땲�뒪 濡쒖쭅�쓣 泥섎━�븯�뒗 �꽌鍮꾩뒪 �겢�옒�뒪�엯�땲�떎.
 *  - �쟾泥� 移댄뀒怨좊━瑜� ���긽�쑝濡� �븳 湲곕��븸 �긽�쐞 10媛� �봽濡쒖젥�듃 議고쉶
 *  - 理쒓렐 �벑濡앸맂 �봽濡쒖젥�듃 3媛� 議고쉶
 *  - �봽濡쒖젥�듃 �긽�꽭 議고쉶 (湲곕��옄 紐⑸줉 �룷�븿)
 *  - 湲곕�(寃곗젣) 泥섎━
 *  - �봽濡쒖젥�듃 �깮�꽦, �닔�젙
 * </p>
 */
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectMapper projectMapper;

    // 파일 업로드 디렉토리 (application.properties 파일에 file.upload-dir 속성으로 지정)
    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 理쒓렐 �떊�꽕�맂 �봽濡쒖젥�듃 3媛� 議고쉶
     *
     * <p>
     * 理쒖떊 �닚�쑝濡� �벑濡앸맂 �봽濡쒖젥�듃 3媛쒖쓽 �젙蹂대�� 議고쉶�븯�뿬 �쟾�떖�빀�땲�떎.
     * �쟾�떖 �빆紐�:
     *  - �봽濡쒖젥�듃 �떇蹂꾩옄, �젣紐�, 移댄뀒怨좊━ID, �깮�꽦�옄 ID, 紐⑺몴湲덉븸, �긽�꽭 �꽕紐�
     *  - �빐�떦 �봽濡쒖젥�듃�쓽 珥� 湲곕��븸 (donationSum)
     *  - 醫뗭븘�슂 �닔 (likeCount)
     * </p>
     *
     * @return 理쒓렐 �떊�꽕�맂 �봽濡쒖젥�듃 3媛� �젙蹂대�� �떞�� RecentProjectDTO 由ъ뒪�듃
     */
    public List<RecentProjectDTO> getLatest3Projects() {
        return projectMapper.findLatest3Projects();
    }    
    
    /**
     * �봽濡쒖젥�듃 �긽�꽭 �젙蹂� 議고쉶
     *
     * <p>
     * �듅�젙 �봽濡쒖젥�듃�쓽 �긽�꽭 �젙蹂대�� 議고쉶�빀�땲�떎.
     * �쟾�떖 �빆紐�:
     *  - �봽濡쒖젥�듃 湲곕낯 �젙蹂�: �떇蹂꾩옄, �젣紐�, 移댄뀒怨좊━ID, �떆�옉�씪, 留덇컧�씪, �긽�꽭 �꽕紐�
     *  - 吏꾪뻾 �긽�솴: �궓�� �씪�닔, 紐⑺몴湲덉븸, �룄�떖�쑉
     *  - 湲곕� 愿��젴 吏묎퀎: 湲곕��옄 �닔, �늻�쟻 湲곕��븸
     *  - �떆媛곸쟻 �젙蹂�: �봽濡쒖젥�듃 �씠誘몄�, 醫뗭븘�슂 �닔
     *  - 湲곕��옄 紐⑸줉: �빐�떦 �봽濡쒖젥�듃�뿉 湲곕��븳 �궗�슜�옄 �땳�꽕�엫/�씠由� 紐⑸줉
     * </p>
     *
     * @param projectId 議고쉶�븷 �봽濡쒖젥�듃�쓽 �떇蹂꾩옄
     * @return ProjectDetailDTO (�긽�꽭 �젙蹂� + 湲곕��옄 紐⑸줉)
     * @throws RuntimeException �봽濡쒖젥�듃媛� 議댁옱�븯吏� �븡�쑝硫� �삁�쇅 諛쒖깮
     */
    public ProjectDetailDTO getProjectDetail(Long projectId) {
        ProjectDetailDTO detail = projectMapper.findProjectDetail(projectId);
        if(detail == null) {
            throw new RuntimeException("�봽濡쒖젥�듃媛� 議댁옱�븯吏� �븡�뒿�땲�떎. id=" + projectId);
        }
        
        List<CategoryDTO> categoryList = projectMapper.findCategorysByProjectId(projectId); // �봽濡쒖젥�듃 移댄뀒怨좊━ 議고쉶
        List<ProjectDonorDTO> donorList = projectMapper.findDonorsByProjectId(projectId); // 湲곕��옄 紐⑸줉 議고쉶
        List<ProjectCommentDTO> commentList = projectMapper.findCommentsByProjectId(projectId); // �봽濡쒖젥�듃 �뙎湲� 議고쉶
        
        detail.setCategoryList(categoryList);
        detail.setDonorList(donorList);
        detail.setCommentList(commentList);
        
        return detail;
    }

    /**
     * 湲곕�(寃곗젣) 泥섎━ 濡쒖쭅
     *
     * <p>
     * �궗�슜�옄媛� �듅�젙 �봽濡쒖젥�듃�뿉 湲곕��븳 �궡�뿭�쓣 donation �뀒�씠釉붿뿉 INSERT�빀�땲�떎.
     * �뙆�씪誘명꽣:
     *  - userId: 湲곕��옄 �떇蹂꾩옄
     *  - projectId: 湲곕� ���긽 �봽濡쒖젥�듃 �떇蹂꾩옄
     *  - amount: 湲곕� 湲덉븸
     *  - paymentMethod: 寃곗젣 諛⑹떇 (�삁: 移대뱶, 怨꾩쥖�씠泥� �벑)
     * </p>
     *
     * @param userId 湲곕��옄 �떇蹂꾩옄
     * @param projectId 湲곕� ���긽 �봽濡쒖젥�듃 �떇蹂꾩옄
     * @param amount 湲곕� 湲덉븸
     * @param paymentMethod 寃곗젣 諛⑹떇
     * @throws RuntimeException 湲곕� �궡�뿭 ���옣�뿉 �떎�뙣�븯硫� �삁�쇅 諛쒖깮
     */
    public void donate(int userId, Long projectId, int amount, String paymentMethod) {
        int rowCount = projectMapper.insertDonation(userId, projectId, amount, paymentMethod);
        if(rowCount <= 0) {
            throw new RuntimeException("湲곕� �궡�뿭 ���옣 �떎�뙣");
        }
    }    
    
    // �봽濡쒖젥�듃 醫뗭븘�슂 移댁슫�똿
    public void like(Long projectId) {
    	projectMapper.updateLikeCount(projectId);
    }
    
    // �봽濡쒖젥�듃 怨듭쑀 �슏�닔 移댁슫�똿
    public void share(Long projectId) {
    	projectMapper.updateShareCount(projectId);
    }
    
    // �뙎湲� 醫뗭븘�슂 移댁슫�똿
    public void likeComment(Long commentId) {
    	projectMapper.updateLikeComment(commentId);
    }
    
    /**
     * �깉濡쒖슫 �봽濡쒖젥�듃 �깮�꽦
     *
     * <p>
     * ProjectEntity 媛앹껜瑜� 諛쏆븘 DB�뿉 �깉濡쒖슫 �봽濡쒖젥�듃 �젅肄붾뱶瑜� 異붽��빀�땲�떎.
     * </p>
     *
     * @param project �깮�꽦�븷 �봽濡쒖젥�듃 �뿏�떚�떚
     */
    public void createProject(ProjectEntity project) throws IOException {
        MultipartFile file = project.getProjectImageFile();
        if (file != null && !file.isEmpty()) {
            // 저장할 디렉토리 생성
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 원본 파일명으로부터 확장자 추출 후 UUID로 새로운 파일명 생성
            String originalFilename = file.getOriginalFilename();
            String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + ext;

            // 파일 저장
            File destination = new File(uploadDir + File.separator + newFilename);
            file.transferTo(destination);

            // 엔티티의 projectImage 필드에 파일명 저장 (필요에 따라 전체 경로나 URL을 저장할 수도 있음)
            project.setProjectImage(newFilename);
        }

        // 파일 처리 후 프로젝트 정보 DB 저장
         projectMapper.insertProject(project);
     }

    /**
     * �봽濡쒖젥�듃 �젙蹂� �닔�젙
     *
     * <p>
     * �뾽�뜲�씠�듃�븷 �봽濡쒖젥�듃 �젙蹂대�� �룷�븿�븳 ProjectEntity 媛앹껜瑜� 諛쏆븘 湲곗〈 �젅肄붾뱶瑜� �닔�젙�빀�땲�떎.
     * </p>
     *
     * @param project �닔�젙�븷 �봽濡쒖젥�듃 �뿏�떚�떚
     */
    public void updateProject(ProjectEntity project) {
        projectMapper.updateProject(project);
    }    

    /**
     * (�쟾泥� 移댄뀒怨좊━) 湲곕��븸 �긽�쐞 10媛� �봽濡쒖젥�듃 議고쉶
     *
     * <p>
     * 移댄뀒怨좊━�뿉 �긽愿��뾾�씠, 湲곕��븸�씠 媛��옣 留롮� �긽�쐞 10媛� �봽濡쒖젥�듃�쓽 �슂�빟 �젙蹂대�� 議고쉶�빀�땲�떎.
     * (媛쒕퀎 移댄뀒怨좊━蹂� �긽�쐞 10媛� 議고쉶�뒗 CategoryService/Controller�뿉�꽌 泥섎━)
     * </p>
     *
     * @return ProjectSummaryDTO 由ъ뒪�듃 (�봽濡쒖젥�듃紐�, 移댄뀒怨좊━ID, 紐⑺몴�씪, �궓�� �씪�닔, 紐⑺몴湲덉븸, �룄�떖�쑉, �씠誘몄�, 醫뗭븘�슂 �닔 �벑)
     */
    public List<ProjectSummaryDTO> getTop10Projects() {
        return projectMapper.findTop10ProjectsByDonation();
    }

    // 전체 프로젝트 목록 조회
    public List<ProjectEntity> getAllProjects() {
        return projectMapper.findAll();
    }

    // 특정 프로젝트 조회
    public ProjectEntity getProjectById(int projectId) {
        return projectMapper.findById(projectId);
    }

    // 프로젝트 상태 업데이트 (승인/거절)
    public ProjectEntity updateProjectStatus(int projectId, String status) {
        ProjectEntity project = projectMapper.findById(projectId);
        if (project != null) {
            project.setStatus(status);
            projectMapper.update(project);
        }
        return project;
    }

    public int deleteProject(int projectId) {
        return projectMapper.deleteProject(projectId);
    }
}
