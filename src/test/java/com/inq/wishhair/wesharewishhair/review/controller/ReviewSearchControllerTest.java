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
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewSimpleResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture.*;
import static com.inq.wishhair.wesharewishhair.global.utils.DefaultPageableUtils.getDateDescPageable;
import static com.inq.wishhair.wesharewishhair.global.utils.DefaultPageableUtils.getLikeDescPageable;
import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ReviewFindControllerTest - WebMvcTest")
public class ReviewSearchControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/review";

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
            PagedResponse<ReviewResponse> expectedResponse = assemblePagedResponse(values().length);
            given(reviewSearchService.findPagedReviews(getLikeDescPageable(10)))
                    .willReturn(expectedResponse);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists()
                    );
        }
    }

    @Nested
    @DisplayName("나의 리뷰 조회 API 테스트")
    class findByReviews {
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
            PagedResponse<ReviewResponse> expectedResponse = assemblePagedResponse(values().length);
            given(reviewSearchService.findMyReviews(1L, getDateDescPageable(10)))
                    .willReturn(expectedResponse);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/my")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists()
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
                    new ResponseWrapper<>(generateReviewSimpleResponse(values().length));
            given(reviewSearchService.findReviewOfMonth()).willReturn(expectedResponse);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/month")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists(),
                            jsonPath("$.result").exists(),
                            jsonPath("$.result.size()").value(values().length)
                    );
        }
    }

    private PagedResponse<ReviewResponse> assemblePagedResponse(int count) {
        Paging defaultPaging = new Paging(count, 0, false);
        return new PagedResponse<>(generateReviewResponses(count), defaultPaging);
    }

    private List<ReviewResponse> generateReviewResponses(int count) {
        User user = UserFixture.B.toEntity();
        HairStyle hairStyle = HairStyleFixture.A.toEntity();

        List<ReviewResponse> result = new ArrayList<>();
        ReviewFixture[] reviewFixtures = values();

        for (int index = 0; index < count; index++) {
            ReviewFixture fixture = reviewFixtures[index];

            Review review = fixture.toEntity(user, hairStyle);
            ReflectionTestUtils.setField(review, "id", 1L + index);
            result.add(new ReviewResponse(review));
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
}
