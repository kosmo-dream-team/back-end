package com.dream_on.springboot.service;

import com.dream_on.springboot.domain.BoardEntity;
import com.dream_on.springboot.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BoardService
 *
 * <p>게시판( board ) 테이블과 관련된 비즈니스 로직을 처리하는 서비스 클래스입니다.
 *  - QNA, NOTICE, DONATION_FEEDBACK, FAQ 등 board_type으로 구분된 게시판을 처리합니다.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    /**
     * 특정 boardType(QNA, NOTICE, DONATION_FEEDBACK, FAQ 등)의 게시글 목록을 조회
     *
     * @param boardType 게시판 유형
     * @return 해당 게시판의 게시글 리스트
     */
    public List<BoardEntity> getPostsByBoardType(String boardType) {
        return boardMapper.findAllByBoardType(boardType);
    }

    /**
     * 게시글 단건 조회
     *
     * @param postId 게시글 식별자
     * @return BoardEntity
     */
    public BoardEntity getPost(int postId) {
        return boardMapper.findByPostId(postId);
    }

    /**
     * 새 게시글 작성
     *
     * @param board 게시글 엔티티
     * @return 생성된 postId (auto_increment)
     */
    public int createPost(BoardEntity board) {
        // status, priority 등 기본값 설정 가능
        boardMapper.insertBoard(board);
        return board.getPostId(); // auto_increment로 생성된 ID 반환
    }

    /**
     * 게시글 수정
     *
     * @param board 수정할 게시글 엔티티
     */
    public void updatePost(BoardEntity board) {
        boardMapper.updateBoard(board);
    }

    /**
     * 게시글 삭제 (옵션)
     *
     * @param postId 삭제할 게시글 식별자
     */
    public void deletePost(int postId) {
        boardMapper.deleteBoard(postId);
    }
}
