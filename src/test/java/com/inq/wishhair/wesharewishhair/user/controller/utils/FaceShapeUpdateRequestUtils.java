package com.inq.wishhair.wesharewishhair.user.controller.utils;

import com.inq.wishhair.wesharewishhair.global.utils.MockMultipartFileUtils;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.FaceShapeUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public abstract class FaceShapeUpdateRequestUtils {
    private static final String FILENAME = "hello1.png";

    public static FaceShapeUpdateRequest request() throws IOException {
        MultipartFile file = MockMultipartFileUtils.createMultipartFile(FILENAME, "file");
        return new FaceShapeUpdateRequest(file);
    }
}
