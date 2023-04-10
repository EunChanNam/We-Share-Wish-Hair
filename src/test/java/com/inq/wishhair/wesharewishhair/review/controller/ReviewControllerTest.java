package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewCreateRequest;
import com.inq.wishhair.wesharewishhair.review.controller.utils.ReviewCreateRequestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ReviewControllerTest - WebMvcTest")
public class ReviewControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/review";

    @Nested
    @DisplayName("리뷰 작성 API 테스트")
    class createReview {
        @Test
        @DisplayName("헤더에 Access 토큰을 포함하지 않아서 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            ReviewCreateRequest request = ReviewCreateRequestUtils.createRequest(ReviewFixture.A, 1L);
            MultiValueMap<String, String> params = generateParams(request);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .multipart(BASE_URL)
                    .params(params);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("리뷰를 작성한다")
        void success() throws Exception {
            //given
            ReviewCreateRequest request = ReviewCreateRequestUtils.createRequest(ReviewFixture.A, 1L);
            given(reviewService.createReview(request, 1L)).willReturn(1L);

            MultiValueMap<String, String> params = generateParams(request);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .multipart(BASE_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .params(params);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isCreated(),
                            jsonPath("$").exists(),
                            jsonPath("$.success").value(true)
                    );
        }
    }

    @Nested
    @DisplayName("리뷰 삭제 API 테스트")
    class deleteReview {
        @Test
        @DisplayName("헤더에 Access 토큰을 포함하지 않아서 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .delete(BASE_URL + "/1");

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("리뷰를 삭제한다")
        void success() throws Exception {
            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .delete(BASE_URL + "/1")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists(),
                            jsonPath("$.success").value(true)
                    );
        }
    }

    private MultiValueMap<String, String> generateParams(ReviewCreateRequest request) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("contents", request.getContents());
        params.add("score", "4.5");
        params.add("hairStyleId", "1");
        return params;
    }
}
