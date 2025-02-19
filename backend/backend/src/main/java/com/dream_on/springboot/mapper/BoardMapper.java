package com.dream_on.springboot.mapper;

import com.dream_on.springboot.domain.BoardEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * BoardMapper
 *
 * <p>board 테이블을 다루는 MyBatis 인터페이스입니다.
 * board_type을 활용하여 QNA, NOTICE, DONATION_FEEDBACK, FAQ를 구분합니다.</p>
 */
@Mapper
public interface BoardMapper {

    /**
     * 특정 boardType의 게시글 목록을 최신 순으로 조회합니다.
     * 
     * @param boardType 게시판 유형(QNA, NOTICE, DONATION_FEEDBACK, FAQ 등)
     * @return 해당 유형의 게시글 리스트
     */
    @Select("""
        SELECT 
            post_id, board_type, user_id, title, content,
            created_at, updated_at, view_count, status, priority, image, category
        FROM board
        WHERE board_type = #{boardType}
        ORDER BY post_id DESC
        """)
    List<BoardEntity> findAllByBoardType(@Param("boardType") String boardType);

    /**
     * 게시글 단건 조회 (post_id)
     * 
     * @param postId 게시글 식별자
     * @return 게시글 정보
     */
    @Select("""
        SELECT 
            post_id, board_type, user_id, title, content,
            created_at, updated_at, view_count, status, priority, image, category
        FROM board
        WHERE post_id = #{postId}
        """)
    BoardEntity findByPostId(@Param("postId") int postId);

    /**
     * 게시글 삽입
     * 
     * @param board 게시글 엔티티
     * @return 영향받은 행 수 (1이면 성공)
     */
    @Insert("""
        INSERT INTO board (
            board_type, user_id, title, content,
            created_at, updated_at, view_count,
            status, priority, image, category
        ) VALUES (
            #{boardType}, #{userId}, #{title}, #{content},
            NOW(), NOW(), 0,
            #{status}, #{priority}, #{image}, #{category}
        )
        """)
    @Options(useGeneratedKeys = true, keyProperty = "postId")
    int insertBoard(BoardEntity board);

    /**
     * 게시글 수정
     *
     * @param board 수정할 게시글 엔티티
     * @return 영향받은 행 수
     */
    @Update("""
        UPDATE board
        SET
            title = #{title},
            content = #{content},
            updated_at = NOW(),
            view_count = #{viewCount},
            status = #{status},
            priority = #{priority},
            image = #{image},
            category = #{category}
        WHERE post_id = #{postId}
        """)
    int updateBoard(BoardEntity board);

    /**
     * 게시글 삭제 (옵션)
     * 
     * @param postId 삭제할 게시글 식별자
     * @return 영향받은 행 수
     */
    @Delete("DELETE FROM board WHERE post_id = #{postId}")
    int deleteBoard(@Param("postId") int postId);
}
