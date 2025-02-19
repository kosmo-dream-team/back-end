package com.dream_on.springboot.controller;

import com.dream_on.springboot.domain.BoardEntity;
import com.dream_on.springboot.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BoardController
 *
 * <p>단일 테이블(board)에 저장된 4가지 게시판(QNA, NOTICE, DONATION_FEEDBACK, FAQ)을 
 * 프론트엔드에 전달하기 위한 컨트롤러입니다.</p>
 *
 * <ul>
 *   <li>QNA:  /board/qna</li>
 *   <li>NOTICE: /board/notice</li>
 *   <li>DONATION_FEEDBACK: /board/feedback</li>
 *   <li>FAQ: /board/faq</li>
 * </ul>
 */
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * QNA 게시판 목록 조회
     *
     * <p>
     * board_type = "QNA" 인 게시글들을 반환합니다.
     * </p>
     *
     * @return QNA 게시글 목록
     */
    @GetMapping("/qna")
    public ResponseEntity<List<BoardEntity>> getQnaPosts() {
        List<BoardEntity> posts = boardService.getPostsByBoardType("QNA");
        return ResponseEntity.ok(posts);
    }

    /**
     * NOTICE 게시판 목록 조회
     *
     * <p>
     * board_type = "NOTICE" 인 게시글들을 반환합니다.
     * </p>
     *
     * @return NOTICE 게시글 목록
     */
    @GetMapping("/notice")
    public ResponseEntity<List<BoardEntity>> getNoticePosts() {
        List<BoardEntity> posts = boardService.getPostsByBoardType("NOTICE");
        return ResponseEntity.ok(posts);
    }

    /**
     * DONATION_FEEDBACK 게시판 목록 조회
     *
     * <p>
     * board_type = "DONATION_FEEDBACK" 인 게시글들을 반환합니다.
     * 기부 후기(피드백) 게시글 등을 보여줍니다.
     * </p>
     *
     * @return DONATION_FEEDBACK 게시글 목록
     */
    @GetMapping("/feedback")
    public ResponseEntity<List<BoardEntity>> getDonationFeedbackPosts() {
        List<BoardEntity> posts = boardService.getPostsByBoardType("DONATION_FEEDBACK");
        return ResponseEntity.ok(posts);
    }

    /**
     * FAQ 게시판 목록 조회
     *
     * <p>
     * board_type = "FAQ" 인 게시글들을 반환합니다.
     * FAQ의 경우 category 필드를 활용해 세부 분류를 관리할 수도 있습니다.
     * </p>
     *
     * @return FAQ 게시글 목록
     */
    @GetMapping("/faq")
    public ResponseEntity<List<BoardEntity>> getFaqPosts() {
        List<BoardEntity> posts = boardService.getPostsByBoardType("FAQ");
        return ResponseEntity.ok(posts);
    }

    /**
     * 게시글 단건 조회
     *
     * <p>
     * post_id를 기반으로 특정 게시글을 가져옵니다.
     * </p>
     *
     * @param postId 게시글 식별자
     * @return BoardEntity
     */
    @GetMapping("/{postId}")
    public ResponseEntity<BoardEntity> getPost(@PathVariable("postId") int postId) {
        BoardEntity post = boardService.getPost(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    /**
     * 게시글 생성
     *
     * <p>
     * board_type, title, content 등 필수 필드를 포함하여 새 게시글을 생성합니다.
     * </p>
     *
     * @param board 생성할 게시글 정보
     * @return 생성된 게시글 ID
     */
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody BoardEntity board) {
        int newId = boardService.createPost(board);
        return ResponseEntity.ok("게시글이 생성되었습니다. postId=" + newId);
    }

    /**
     * 게시글 수정
     *
     * <p>
     * 기존 게시글의 title, content, status, priority, image, category 등을 수정할 수 있습니다.
     * </p>
     *
     * @param postId 수정할 게시글 식별자
     * @param board 수정할 게시글 정보
     * @return 성공 메시지
     */
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable("postId") int postId,
                                             @RequestBody BoardEntity board) {
        board.setPostId(postId);
        boardService.updatePost(board);
        return ResponseEntity.ok("게시글이 수정되었습니다. postId=" + postId);
    }

    /**
     * 게시글 삭제 (옵션)
     *
     * @param postId 삭제할 게시글 식별자
     * @return 성공 메시지
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") int postId) {
        boardService.deletePost(postId);
        return ResponseEntity.ok("게시글이 삭제되었습니다. postId=" + postId);
    }
}
