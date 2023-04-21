package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ReviewFindControllerTest - WebMvcTest")
public class ReviewFindControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/review";

    @Nested
    @DisplayName("리뷰 단건 조회 API 테스트")
    class findReviewById {
        @Test
        @DisplayName("리뷰의 아이디를 통해 단건으로 조회한다")
        void success() throws Exception {
            //given
            ReviewResponse expectedResponse = generateReviewResponses();
            given(reviewFindService.findReviewById(1L, 1L))
                    .willReturn(expectedResponse);

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
                                            parameterWithName("reviewId").description("조회할 리뷰 아이디")
                                    ),
                                    reviewResponseDocument()
                            )
                    );
        }

        @Test
        @DisplayName("헤더에 Access 토큰을 포함하지 않아서 예외를 던진다")
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

    private ReviewResponse generateReviewResponses() {
        User user = UserFixture.B.toEntity();
        ReflectionTestUtils.setField(user, "id", 1L);

        HairStyle hairStyle = HairStyleFixture.A.toEntity();

        Review review = ReviewFixture.A.toEntity(user, hairStyle);
        ReflectionTestUtils.setField(review, "id", 1L);

        return new ReviewResponse(review, 1L);
    }

    private ResponseFieldsSnippet reviewResponseDocument() {
        return responseFields(
                fieldWithPath("reviewId").description("리뷰 아이디"),
                fieldWithPath("hairStyleName").description("헤어스타일 이름"),
                fieldWithPath("userNickname").description("작성자 닉네임"),
                fieldWithPath("score").description("리뷰 점수"),
                fieldWithPath("contents").description("리뷰 내용"),
                fieldWithPath("createdDate").description("리뷰 작성 일"),
                fieldWithPath("photos").optional().description("사진이 없을 수 있음"),
                fieldWithPath("photos[].storeUrl").description("리뷰 사진 URI"),
                fieldWithPath("likes").description("좋아요 수"),
                fieldWithPath("hashTags[].tag").description("해시 태그"),
                fieldWithPath("writer").description("작성자 여부")
        );
    }
}
