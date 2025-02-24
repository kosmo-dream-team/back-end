package com.dream_on.springboot.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.dream_on.springboot.domain.ProjectEntity;
import com.dream_on.springboot.dto.ProjectDetailDTO;
import com.dream_on.springboot.dto.ProjectDonationDTO;
import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.dto.RecentProjectDTO;
import com.dream_on.springboot.service.ProjectService;

import lombok.RequiredArgsConstructor;

/**
 * ProjectController
 *
 * <p>�봽濡쒖젥�듃 愿��젴 API瑜� �젣怨듯븯�뒗 而⑦듃濡ㅻ윭�엯�땲�떎.
 *  - �쟾泥� 移댄뀒怨좊━瑜� ���긽�쑝濡� �븳 �긽�쐞 10媛� �봽濡쒖젥�듃 議고쉶
 *  - �봽濡쒖젥�듃 �긽�꽭 議고쉶
 *  - 湲곕�(寃곗젣) 泥섎━
 *  - �봽濡쒖젥�듃 �깮�꽦/�닔�젙
 *  
 *  (媛쒕퀎 移댄뀒怨좊━�뿉 ���븳 �긽�쐞 10媛� �봽濡쒖젥�듃�뒗 CategoryController�뿉�꽌 泥섎━)
 * </p>
 */
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * (�쟾泥� 移댄뀒怨좊━) 湲곕��븸 �긽�쐞 10媛� �봽濡쒖젥�듃 議고쉶
     *
     * <p>
     * 湲곕��븸�씠 媛��옣 留롮� �긽�쐞 10媛� �봽濡쒖젥�듃瑜� 諛섑솚�빀�땲�떎.
     * 移댄뀒怨좊━ 援щ텇 �뾾�씠 �쟾泥� �봽濡쒖젥�듃 以� �긽�쐞 10媛쒕쭔 議고쉶�빀�땲�떎.
     * (媛쒕퀎 移댄뀒怨좊━�뒗 CategoryController瑜� 李몄“)
     * </p>
     *
     * @return ProjectSummaryDTO 由ъ뒪�듃
     */
    @GetMapping("/top10")
    public ResponseEntity<List<ProjectSummaryDTO>> getTop10Projects() {
        List<ProjectSummaryDTO> topProjects = projectService.getTop10Projects();
        return ResponseEntity.ok(topProjects);
    }


    /**
     * 理쒓렐 �벑濡앸맂 �봽濡쒖젥�듃 3媛�
     *
     * <p>
     * (移댄뀒怨좊━ 援щ텇 �뾾�씠) 理쒓렐 �벑濡앸맂 �봽濡쒖젥�듃 3媛쒖쓽 �젙蹂대�� 議고쉶�빀�땲�떎.
     * �븘�슂 �떆, �떎瑜� 而⑦듃濡ㅻ윭�뿉�꽌 泥섎━�빐�룄 �릺吏�留�, 
     * �뿬湲곗꽌�뒗 ProjectService�뿉 硫붿꽌�뱶媛� �엳�쑝誘�濡� 媛꾨떒�엳 異붽��뻽�뒿�땲�떎.
     * </p>
     *
     * @return List<RecentProjectDTO>
     */
    @GetMapping("/latest")
    public ResponseEntity<List<RecentProjectDTO>> getLatestProjects() {
        List<RecentProjectDTO> latestProjects = projectService.getLatest3Projects();
        return ResponseEntity.ok(latestProjects);
    }    
    
    /**
     * �봽濡쒖젥�듃 �긽�꽭 �젙蹂� 議고쉶
     *
     * <p>
     * �듅�젙 �봽濡쒖젥�듃�쓽 �긽�꽭 �젙蹂대�� 諛섑솚�빀�땲�떎.
     * </p>
     *
     * @param projectId 議고쉶�븷 �봽濡쒖젥�듃 �떇蹂꾩옄
     * @return ProjectDetailDTO (�봽濡쒖젥�듃 湲곕낯 �젙蹂�, 吏꾪뻾 �긽�솴, 湲곕� 吏묎퀎, �씠誘몄�, 醫뗭븘�슂 �닔, 湲곕��옄 紐⑸줉 �룷�븿)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDetailDTO> getProjectDetail(@PathVariable("id") Long projectId) {
    	ProjectDetailDTO detail = projectService.getProjectDetail(projectId);
    	if (detail == null) {
    		return ResponseEntity.notFound().build();
    	}
        return ResponseEntity.ok(detail);
    }

    /**
     * 湲곕�(寃곗젣) 泥섎━
     *
     * <p>
     * �궗�슜�옄媛� �듅�젙 �봽濡쒖젥�듃�뿉 湲곕��븷 �븣 湲곕� �궡�뿭�쓣 ���옣�빀�땲�떎.
     * �뙆�씪誘명꽣:
     *  - userId: 湲곕��옄 �떇蹂꾩옄
     *  - amount: 湲곕� 湲덉븸
     *  - paymentMethod: 寃곗젣 諛⑹떇 (�삁: 移대뱶, 怨꾩쥖�씠泥�)
     * </p>
     *
     * @param projectId 湲곕� ���긽 �봽濡쒖젥�듃 �떇蹂꾩옄
     * @param userId 湲곕��옄 �떇蹂꾩옄
     * @param amount 湲곕� 湲덉븸
     * @param paymentMethod 寃곗젣 諛⑹떇
     * @return �꽦怨� 硫붿떆吏� ("湲곕� �꽦怨�!")
     */
    @PostMapping("/{id}/donate")
    public ResponseEntity<String> donate(@PathVariable("id") Long projectId,
                                         @RequestBody ProjectDonationDTO projectDonationDTO) {
    	System.out.println(projectDonationDTO);
        projectService.donate(projectDonationDTO.getUserId(), projectId, projectDonationDTO.getAmount(), projectDonationDTO.getPaymentMethod());
        return ResponseEntity.ok("湲곕� �꽦怨�!");
    }

    // 醫뗭븘�슂 �닔 �뾽�뜲�씠�듃
    @GetMapping("/{id}/like")
    public ResponseEntity<String> like(@PathVariable("id") Long projectId) {
    	projectService.like(projectId);
    	return ResponseEntity.ok("醫뗭븘�슂 �꽦怨�!");
    }
    
    // 怨듭쑀 �슏�닔 �뾽�뜲�씠�듃
    @GetMapping("/{id}/share")
    public ResponseEntity<String> share(@PathVariable("id") Long projectId) {
    	projectService.share(projectId);
    	return ResponseEntity.ok("怨듭쑀 �꽦怨�!");
    }
    
    // �뙎湲� 醫뗭븘�슂 �닔 �뾽�뜲�씠�듃
    @GetMapping("/likeComment/{commentId}")
    public ResponseEntity<String> likeComment(@PathVariable("commentId") Long commentId) {
    	projectService.likeComment(commentId);
    	return ResponseEntity.ok("�뙎湲� 醫뗭븘�슂 �꽦怨�!");
    }
    
    /**
     * �봽濡쒖젥�듃 �깮�꽦
     *
     * <p>
     * �봽濡좏듃�뿏�뱶�뿉�꽌 �쟾�떖諛쏆� �봽濡쒖젥�듃 �젙蹂대�� 湲곕컲�쑝濡� �깉濡쒖슫 �봽濡쒖젥�듃瑜� �깮�꽦�빀�땲�떎.
     * </p>
     *
     * @param project �깮�꽦�븷 �봽濡쒖젥�듃 �젙蹂대�� �떞�� ProjectEntity
     * @return �꽦怨� 硫붿떆吏� ("�봽濡쒖젥�듃媛� �깮�꽦�릺�뿀�뒿�땲�떎.")
     */
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createProject(@ModelAttribute ProjectEntity project) {
        try {
        	projectService.createProject(project);
            return ResponseEntity.ok("�봽濡쒖젥�듃媛� �깮�꽦�릺�뿀�뒿�땲�떎.");
        } catch (Exception e) {
        	// 濡쒓퉭 �벑 異붽� 泥섎━ 媛��뒫
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        						.body("�봽濡쒖젥�듃 �깮�꽦 以� �삤瑜섍� 諛쒖깮�뻽�뒿�땲�떎.");
        }
    }

    /**
     * �봽濡쒖젥�듃 �닔�젙
     *
     * <p>
     * �듅�젙 �봽濡쒖젥�듃�쓽 �젙蹂대�� �닔�젙�빀�땲�떎.
     * URL 寃쎈줈�뿉�꽌 �봽濡쒖젥�듃 �떇蹂꾩옄瑜� 諛쏄퀬, 蹂몃Ц�뿉 �닔�젙�븷 �뜲�씠�꽣瑜� �떞�� ProjectEntity瑜� �쟾�떖�빀�땲�떎.
     * </p>
     *
     * @param projectId �닔�젙�븷 �봽濡쒖젥�듃 �떇蹂꾩옄
     * @param project �닔�젙�븷 �봽濡쒖젥�듃 �젙蹂대�� �떞�� ProjectEntity
     * @return �꽦怨� 硫붿떆吏� ("�봽濡쒖젥�듃媛� �닔�젙�릺�뿀�뒿�땲�떎.")
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProject(@PathVariable("id") int projectId,
                                                @RequestBody ProjectEntity project) {
        project.setProjectId(projectId);
        projectService.updateProject(project);
        return ResponseEntity.ok("�봽濡쒖젥�듃媛� �닔�젙�릺�뿀�뒿�땲�떎.");
    }

}
