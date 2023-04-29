package com.inq.wishhair.wesharewishhair.review.controller.utils;

import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.utils.MockMultipartFileUtils;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewCreateRequest;

import java.io.IOException;


public abstract class ReviewCreateRequestUtils {
    private static final String FILE_PATH = "src/test/resources/images/";

    public static ReviewCreateRequest createRequest(ReviewFixture fixture, Long hairStyleId) throws IOException {
        return new ReviewCreateRequest(
                fixture.getContents(),
                fixture.getScore(),
                MockMultipartFileUtils.generateFiles(fixture.getStoreUrls()),
                hairStyleId
        );
    }
}
