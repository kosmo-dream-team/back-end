package com.dream_on.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.dream_on.springboot.dto.CommentDTO;

@Mapper
public interface CommentMapper {
	int writeComment(CommentDTO comment);
}
