package com.dream_on.springboot.domain;

import lombok.Data;

/**
 * DB의 'category' 테이블과 1:1 매핑되는 엔티티
 * 컬럼:
 *  - category_id (PK, int)
 *  - category (varchar(30))
 */
@Data
public class CategoryEntity {
    private int categoryId;    // category_id
    private String category;   // 카테고리명 (ex: "교육", "의료", "환경" 등)
}
