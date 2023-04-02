package com.inq.wishhair.wesharewishhair.review.controller.utils;

import com.inq.wishhair.wesharewishhair.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewCreateRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public abstract class ReviewCreateRequestUtils {
    private static final String FILE_PATH = "src/test/resources/images/";

    public static ReviewCreateRequest createRequest(ReviewFixture fixture, Long hairStyleId) throws IOException {
        return new ReviewCreateRequest(
                fixture.getContents(),
                fixture.getScore(),
                generateFiles(fixture),
                hairStyleId
        );
    }

    private static List<MultipartFile> generateFiles(ReviewFixture fixture) throws IOException {
        List<MultipartFile> files = new ArrayList<>();
        for (String originalFilename : fixture.getOriginalFilenames()) {
            files.add(createMultipartFile(originalFilename));
        }
        return files;
    }

    private static MultipartFile createMultipartFile(String originalFilename) throws IOException {
        try (FileInputStream stream = new FileInputStream(FILE_PATH + originalFilename)) {
            return new MockMultipartFile("files", originalFilename, "image/bmp", stream);
        }
    }
}
