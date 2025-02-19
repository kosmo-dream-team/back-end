package com.dream_on.springboot.domain;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * BoardEntity
 *
 * <p>DB의 'board' 테이블과 1:1 매핑되는 도메인 클래스입니다.
 * 게시판 유형(board_type)에 따라 QNA, NOTICE, DONATION_FEEDBACK, FAQ를 구분합니다.</p>
 *
 * <ul>
 *   <li>postId: 게시글 식별자 (PK, auto_increment)</li>
 *   <li>boardType: 게시판 유형 (QNA, NOTICE, DONATION_FEEDBACK, FAQ 등)</li>
 *   <li>userId: 작성자(회원) 식별자</li>
 *   <li>title: 게시글 제목</li>
 *   <li>content: 게시글 내용</li>
 *   <li>createdAt, updatedAt: 작성/수정 시간</li>
 *   <li>viewCount: 조회수 (QNA/NOTICE 등에서 사용)</li>
 *   <li>status: 게시글 상태 (예: open, closed, active, etc.)</li>
 *   <li>priority: 우선순위 (예: normal, high) - NOTICE 등에서 사용</li>
 *   <li>image: 첨부 이미지 경로 - DONATION_FEEDBACK 등에서 사용</li>
 *   <li>category: FAQ 등에서 카테고리 구분</li>
 * </ul>
 */
@Data
public class BoardEntity {
    private int postId;
    private String boardType;    // QNA, NOTICE, DONATION_FEEDBACK, FAQ 등
    private int userId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int viewCount;
    private String status;
    private String priority;
    private String image;
    private String category;
}
