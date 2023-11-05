package com.inq.wishhair.wesharewishhair.user.utils;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.RecommendResponse;

public interface AiConnector {
    Tag detectFaceShape(MultipartFile file);

    RecommendResponse recommend(MultipartFile file, List<Tag> tags);
}
