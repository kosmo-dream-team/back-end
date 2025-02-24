package com.dream_on.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dream_on.springboot.dto.CommentDTO;
import com.dream_on.springboot.service.CommentService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;
	
	@PostMapping("/write")
	public ResponseEntity<String> writeComment(@RequestBody CommentDTO commentDTO) {
		System.out.println("commentDTO: " + commentDTO);
		commentService.writeComment(commentDTO);
		return ResponseEntity.ok("댓글이 작성되었습니다.");
	}
}