package com.inq.wishhair.wesharewishhair.review.controller.utils;

import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewUpdateRequest;

import java.io.IOException;

import static com.inq.wishhair.wesharewishhair.global.utils.MockMultipartFileUtils.generateFiles;

public abstract class ReviewUpdateRequestUtils {

    public static ReviewUpdateRequest request(Long reviewId, ReviewFixture fixture) throws IOException {
        return new ReviewUpdateRequest(
                reviewId,
                fixture.getContents(),
                fixture.getScore(),
                generateFiles(fixture.getStoreUrls())
        );
    }

    public static ReviewUpdateRequest wrongContentsRequest(Long reviewId, ReviewFixture fixture) throws IOException {
        return new ReviewUpdateRequest(
                reviewId,
                "XX",
                fixture.getScore(),
                generateFiles(fixture.getStoreUrls())
        );
    }
}
