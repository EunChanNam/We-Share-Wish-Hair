package com.inq.wishhair.wesharewishhair.user.utils;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.Tag;
import org.springframework.web.multipart.MultipartFile;

@FunctionalInterface
public interface AiConnector {
    Tag detectFaceShape(MultipartFile file);
}
