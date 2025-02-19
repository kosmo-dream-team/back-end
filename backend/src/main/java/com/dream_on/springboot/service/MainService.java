package com.dream_on.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dream_on.springboot.dto.ProjectSummaryDTO;
import com.dream_on.springboot.mapper.MainMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MainMapper mainMapper;

    /**
     * 인기 TOP15 프로젝트 리스트 반환
     */
    public List<ProjectSummaryDTO> getTop15Projects() {
        return mainMapper.findTop15ProjectsByDonation();
    }
}
