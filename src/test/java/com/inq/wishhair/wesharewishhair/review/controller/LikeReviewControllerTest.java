package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("LikeReviewControllerTest - WebMvcTest")
public class LikeReviewControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/review/like";

    @Nested
    @DisplayName("좋아요 API 테스트")
    class executeLike {
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
                                            parameterWithName("reviewId").description("좋아요 할 리뷰 ID(PK)")
                                    ),
                                    successResponseDocument()
                            )
                    );
        }

        @Test
        @DisplayName("좋아요 돼있는 리뷰에 좋아요를 실행해 실패한다")
        void failByAlreadyExistLike() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.REVIEW_ALREADY_LIKING;
            doThrow(new WishHairException(expectedError)).when(likeReviewService).executeLike(any(), any());

            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .post(BASE_URL + "/{reviewId}", 1L)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            assertException(expectedError, requestBuilder, status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("좋아요 취소 API 테스트")
    class cancelLike {
        @Test
        @DisplayName("좋아요를 취소한다")
        void success() throws Exception {
            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .post(BASE_URL + "/cancel/{reviewId}", 1L)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    pathParameters(
                                            parameterWithName("reviewId").description("좋아요 취소 할 리뷰 ID(PK)")
                                    ),
                                    successResponseDocument()
                            )
                    );
        }

        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 실패한다")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .post(BASE_URL + "/cancel/{reviewId}", 1L);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("좋아요 되있지 않은 리뷰에 좋아요 취소를 실행해 실패한다")
        void failByLikeIsNotExist() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.REVIEW_NOT_LIKING;
            doThrow(new WishHairException(expectedError)).when(likeReviewService).cancelLike(any(), any());

            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .post(BASE_URL + "/cancel/{reviewId}", 1L)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            assertException(expectedError, requestBuilder, status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("좋아요 여부 조회 API 테스트")
    class checkIsLiking {
        @Test
        @DisplayName("사용자의 입력한 id 에 해당하는 리뷰에 대한 좋아요 여부를 조회한다")
        void success() throws Exception {
            //given
            given(likeReviewService.checkIsLiking(any(), any())).willReturn(true);

            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .get(BASE_URL + "/{reviewId}", 1L)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    pathParameters(
                                            parameterWithName("reviewId").attributes(constraint("필수 입니다"))
                                                    .description("확인할 리뷰 ID")
                                    ),
                                    responseFields(
                                            fieldWithPath("isLiking").description("좋아요 여부")
                                    )
                            )
                    );
        }

        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 실패")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .get(BASE_URL + "/{reviewId}", 1L);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }
    }
}
