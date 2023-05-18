package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.Paging;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.infra.query.dto.response.ReviewQueryResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewDetailResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponseAssembler;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewSimpleResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture.*;
import static com.inq.wishhair.wesharewishhair.global.utils.DefaultPageableUtils.getDateDescPageable;
import static com.inq.wishhair.wesharewishhair.global.utils.DefaultPageableUtils.getLikeDescPageable;
import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ReviewFindControllerTest - WebMvcTest")
public class ReviewSearchControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/review";

    @Nested
    @DisplayName("리뷰 단건 조회 API 테스트")
    class findReviewById {
        @Test
        @DisplayName("리뷰의 아이디를 통해 단건으로 조회한다")
        void success() throws Exception {
            //given
            ReviewDetailResponse expectedResponse = generateReviewDetailResponse(2L);
            given(reviewSearchService.findReviewById(1L, 1L))
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
                                    reviewDetailResponseDocument()
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

    @Nested
    @DisplayName("전체 리뷰 조회 API 테스트")
    class findPagingReviews {
        @Test
        @DisplayName("헤더에 Access 토큰을 포함하지 않아서 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("전체 리뷰를 조회한다")
        void success() throws Exception {
            //given
            PagedResponse<ReviewResponse> expectedResponse = assemblePagedResponse(2, 2L);

            given(reviewSearchService.findPagedReviews(any(), any()))
                    .willReturn(expectedResponse);

            //when
            Pageable pageable = getLikeDescPageable(10);
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .queryParams(generatePageableParams(pageable));

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists()
                    ).andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    pageableParametersDocument(10, "likeReviews.likes.desc"),
                                    pagedReviewResponseDocument()
                            )
                    );
        }
    }

    @Nested
    @DisplayName("나의 리뷰 조회 API 테스트")
    class findMyReviews {

        @Test
        @DisplayName("헤더에 Access 토큰을 포함하지 않아서 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/my");

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("나의 리뷰를 조회한다")
        void success() throws Exception {
            //given
            PagedResponse<ReviewResponse> expectedResponse = assemblePagedResponse(2, 1L);
            given(reviewSearchService.findMyReviews(any(), any()))
                    .willReturn(expectedResponse);

            //when
            Pageable pageable = getDateDescPageable(10);
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/my")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .queryParams(generatePageableParams(pageable));

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists()
                    ).andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    pageableParametersDocument(10, "createdDate.desc"),
                                    pagedReviewResponseDocument()
                            )
                    );
        }

    }
    @Nested
    @DisplayName("이달의 추천 리뷰 조회 API")
    class findReviewOfMonth {
        @Test
        @DisplayName("헤더에 Access 토큰을 포함하지 않아서 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/month");

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("이달의 추천 리뷰를 조회한다")
        void success() throws Exception {
            //given
            ResponseWrapper<ReviewSimpleResponse> expectedResponse =
                    new ResponseWrapper<>(generateReviewSimpleResponse(2));
            given(reviewSearchService.findReviewOfMonth()).willReturn(expectedResponse);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/month")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists()
                    ).andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    simpleReviewResponseDocument()
                            )
                    );
        }

    }

    private PagedResponse<ReviewResponse> assemblePagedResponse(int count, Long userId) {
        Paging defaultPaging = new Paging(count, 0, false);
        return new PagedResponse<>(generateReviewResponses(count, userId), defaultPaging);
    }

    private ReviewDetailResponse generateReviewDetailResponse(Long userId) {
        User user = UserFixture.B.toEntity();
        ReflectionTestUtils.setField(user, "id", 1L);

        HairStyle hairStyle = HairStyleFixture.A.toEntity();

        Review review = ReviewFixture.A.toEntity(user, hairStyle);
        ReviewQueryResponse queryResponse = new ReviewQueryResponse(review, 0);
        ReflectionTestUtils.setField(review, "id", 1L);
        ReviewDetailResponse response = ReviewResponseAssembler.toReviewDetailResponse(queryResponse, false);
        response.getReviewResponse().addIsWriter(userId);

        return response;
    }

    private List<ReviewResponse> generateReviewResponses(int count, Long userId) {
        User user = UserFixture.B.toEntity();
        ReflectionTestUtils.setField(user, "id", 1L);

        HairStyle hairStyle = HairStyleFixture.A.toEntity();

        List<ReviewResponse> result = new ArrayList<>();
        ReviewFixture[] reviewFixtures = values();

        for (int index = 0; index < count; index++) {
            ReviewFixture fixture = reviewFixtures[index];

            Review review = fixture.toEntity(user, hairStyle);
            ReviewQueryResponse queryResponse = new ReviewQueryResponse(review, 0);
            ReflectionTestUtils.setField(review, "id", 1L + index);
            ReviewResponse reviewResponse = ReviewResponseAssembler.toReviewResponse(queryResponse);
            reviewResponse.addIsWriter(userId);

            result.add(reviewResponse);
        }

        return result;
    }

    private List<ReviewSimpleResponse> generateReviewSimpleResponse(int count) {
        User user = UserFixture.B.toEntity();
        HairStyle hairStyle = HairStyleFixture.A.toEntity();

        List<ReviewSimpleResponse> result = new ArrayList<>();
        ReviewFixture[] reviewFixtures = values();

        for (int index = 0; index < count; index++) {
            ReviewFixture fixture = reviewFixtures[index];

            Review review = fixture.toEntity(user, hairStyle);
            ReflectionTestUtils.setField(review, "id", 1L + index);
            result.add(new ReviewSimpleResponse(review));
        }

        return result;
    }

    private ResponseFieldsSnippet reviewResponseDocument(String resultWrapper) {

        return responseFields(
                fieldWithPath(resultWrapper + "reviewId").description("리뷰 아이디"),
                fieldWithPath(resultWrapper + "hairStyleName").description("헤어스타일 이름"),
                fieldWithPath(resultWrapper + "userNickname").description("작성자 닉네임"),
                fieldWithPath(resultWrapper + "score").description("리뷰 점수"),
                fieldWithPath(resultWrapper + "contents").description("리뷰 내용"),
                fieldWithPath(resultWrapper + "createdDate").description("리뷰 작성 일"),
                fieldWithPath(resultWrapper + "photos").optional().description("사진이 없을 수 있음"),
                fieldWithPath(resultWrapper + "photos[].storeUrl").description("리뷰 사진 URI"),
                fieldWithPath(resultWrapper + "likes").description("좋아요 수"),
                fieldWithPath(resultWrapper + "hashTags[].tag").description("해시 태그"),
                fieldWithPath(resultWrapper + "writer").description("작성자 여부")
        );
    }

    private ResponseFieldsSnippet reviewDetailResponseDocument() {
        return reviewResponseDocument("reviewResponse.").and(
                fieldWithPath("liking").description("좋아요 여부")
        );
    }

    private ResponseFieldsSnippet pagedReviewResponseDocument() {
        return reviewResponseDocument("result[].").and(
                fieldWithPath("paging.contentSize").description("조회된 리뷰 개수"),
                fieldWithPath("paging.page").description("현재 페이지"),
                fieldWithPath("paging.hasNext").description("다음 페이지 존재 여부")
        );
    }

    private ResponseFieldsSnippet simpleReviewResponseDocument() {
        return responseFields(
                fieldWithPath("result[].reviewId").description("리뷰 아이디"),
                fieldWithPath("result[].hairStyleName").description("헤어스타일 이름"),
                fieldWithPath("result[].userNickname").description("작성자 닉네임"),
                fieldWithPath("result[].contents").description("리뷰 내용")
        );
    }
}
