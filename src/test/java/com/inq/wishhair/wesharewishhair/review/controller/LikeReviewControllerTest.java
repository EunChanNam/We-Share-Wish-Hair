package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("LikeReviewControllerTest - WebMvcTest")
public class LikeReviewControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/review/like";

    @Nested
    @DisplayName("좋아요 API 테스트")
    class likeReview {
        @Test
        @DisplayName("헤더에 Access 토큰을 포함하지 않아서 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(BASE_URL + "/1");

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("좋아요를 실행한다")
        void success() throws Exception {
            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .post(BASE_URL + "/{reviewId}", 1L)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists(),
                            jsonPath("$.success").value(true)
                    ).andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    pathParameters(
                                            parameterWithName("reviewId").attributes(constraint("필수 입니다"))
                                            .description("좋아요 할 리뷰 ID(PK)")
                                            ),
                                    successResponseDocument()
                            )
                    );
        }
    }
}
