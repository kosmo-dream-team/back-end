package com.dream_on.springboot.dto;

import lombok.Data;

@Data
public class ProjectSummaryDTO {
    // === 프론트에서 요구하는 필드/변수명 그대로 선언 ===
    private int project_id;        // PK
    private String user_name;           // 수혜자 id
    private String category;       // 카테고리 id
    private String title;          // 프로젝트명
    private String project_image;  // 프로젝트 이미지 경로
    private int target_amount;     // 목표 금액

    private int d_daY;     // 모금 마감까지 남은 기간 (대문자 Y)
    private int progresS;  // 모금 진행도(%) (대문자 S)


    
/**
 * ProjectSummaryDTO
 *
 * <p>�씠 DTO�뒗 �긽�쐞 10媛� �봽濡쒖젥�듃 �벑�쓽 媛꾨떒�븳 紐⑸줉�씠�굹 �슂�빟 �젙蹂대�� 蹂댁뿬以� �븣 �궗�슜�맗�땲�떎.
 * �삁瑜� �뱾�뼱, �솃 �솕硫댁쓽 "湲곕� �긽�쐞 10媛� �봽濡쒖젥�듃" �꽮�뀡�쓣 援ъ꽦�븷 �븣 二쇰줈 �벐�엯�땲�떎.</p>
 * <ul>
 *   <li><b>�봽濡쒖젥�듃紐�</b>: projectName</li>
 *   <li><b>移댄뀒怨좊━</b>: category</li>
 *   <li><b>留덇컧�씪</b>: goalDate (臾몄옄�뿴 or Date), daysLeft (�궓�� �씪�닔)</li>
 *   <li><b>紐⑺몴湲덉븸</b>: goalAmount</li>
 *   <li><b>�룄�떖�쑉</b>: donationRate (%)</li>
 *   <li><b>�씠誘몄�</b>: projectImage (URL or �뙆�씪紐�)</li>
 *   <li><b>醫뗭븘�슂 �닔</b>: likeCount</li>
 * </ul>
 * <p>湲곕낯�쟻�쑝濡� �봽濡쒖젥�듃�쓽 �빑�떖 �슂�빟留� 蹂댁뿬二쇰ŉ, �긽�꽭 �젙蹂대뒗 ProjectDetailDTO �벑�뿉�꽌 �솗�씤�빀�땲�떎.</p>
 
@Data
public class ProjectSummaryDTO {
    private String projectName;    // �봽濡쒖젥�듃紐�
    private String category;       // 移댄뀒怨좊━ (臾몄옄�뿴)
    private String goalDate;       // 紐⑺몴�씪(留덇컧�씪) (臾몄옄�뿴 or Date)
    private int daysLeft;          // �궓�� �씪�닔 (�삁: D-16)
    private int goalAmount;        // 紐⑺몴湲덉븸
    private int donationRate;      // �룄�떖�쑉(%) ex: 33
    private String projectImage;   // �봽濡쒖젥�듃 �씠誘몄� 寃쎈줈/URL

    private int likeCount;         // 醫뗭븘�슂 �닔
*/
}
