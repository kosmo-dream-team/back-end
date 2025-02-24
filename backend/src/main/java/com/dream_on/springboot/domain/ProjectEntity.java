package com.dream_on.springboot.domain;

import lombok.Data;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

/**
 * DB 'project' 테이블과 1:1 매핑되는 엔티티
 * 새로 추가된 컬럼으로 'like' (또는 likes)를 가정하고, 코드에서는 likeCount로 사용
 */
@Data
public class ProjectEntity {
    private int projectId;          // project_id (int)
    private int categoryId;         // category_id (int)
    private String title;           // title (varchar(100))
    private int userId;            // user_id (int)
    private int targetAmount;       // target_amount (int)
    private LocalDateTime startDate;  // start_date (datetime)
    private LocalDateTime endDate;    // end_date (datetime)
    private String status;          // status (varchar(20)), e.g. 'pending', 'active'
    private String description;     // description (TEXT)
    private LocalDateTime createdAt; // created_at (datetime)
    private LocalDateTime updatedAt; // updated_at (datetime)
    private String projectImage;    // project_image (varchar(200))
    private int shareCount;         // share_count (int)
    
    // 새로 추가된 컬럼 (DB: 'like' 등)
    private int likeCount;          // 예: DB 컬럼명이 'like'라면 매핑 시 주의
    private MultipartFile projectImageFile;
    private String categoryName;
    private String beneficiaryName;
}
