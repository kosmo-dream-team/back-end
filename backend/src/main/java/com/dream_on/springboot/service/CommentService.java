package com.dream_on.springboot.service;

import org.springframework.stereotype.Service;

import com.dream_on.springboot.dto.CommentDTO;
import com.dream_on.springboot.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentMapper commentMapper;
	
	public void writeComment(CommentDTO comment) {
		commentMapper.writeComment(comment);
	}
}
