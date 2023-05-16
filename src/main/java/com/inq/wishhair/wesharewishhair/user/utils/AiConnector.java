package com.inq.wishhair.wesharewishhair.user.utils;

import org.springframework.web.multipart.MultipartFile;

@FunctionalInterface
public interface AiConnector {
    String detectFaceShape(MultipartFile file);
}
