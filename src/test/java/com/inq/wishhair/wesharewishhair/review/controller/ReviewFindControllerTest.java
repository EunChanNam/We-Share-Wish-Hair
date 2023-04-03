package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.utils.DefaultPageableUtils;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewSimpleResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.fixture.ReviewFixture.*;
import static com.inq.wishhair.wesharewishhair.global.utils.DefaultPageableUtils.getDateDescPageable;
import static com.inq.wishhair.wesharewishhair.global.utils.DefaultPageableUtils.getLikeDescPageable;
import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ReviewFindControllerTest - WebMvcTest")
public class ReviewFindControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/review";

    @Test
    @DisplayName("전체 리뷰 조회 API 테스트")
    void findPagingReviews() throws Exception {
        //given
        Slice<ReviewResponse> expectedResult = generateReviewSliceResponse(values().length);
        given(reviewFindService.findPagedReviews(getLikeDescPageable()))
                .willReturn(expectedResult);

        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(BASE_URL)
                .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

        //then
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").exists(),
                        jsonPath("$.result").exists(),
                        jsonPath("$.paging").exists(),
                        jsonPath("$.paging.hasNext").value(false),
                        jsonPath("$.paging.contentSize").value(values().length)
                );
    }

    @Test
    @DisplayName("나의 리뷰 조회 API")
    void findMyReviews() throws Exception {
        //given
        Slice<ReviewResponse> expectedResult = generateReviewSliceResponse(values().length);
        given(reviewFindService.findMyReviews(1L, getDateDescPageable()))
                .willReturn(expectedResult);

        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(BASE_URL + "/my")
                .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

        //then
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").exists(),
                        jsonPath("$.result").exists(),
                        jsonPath("$.paging").exists(),
                        jsonPath("$.paging.hasNext").value(false),
                        jsonPath("$.paging.contentSize").value(values().length)
                );
    }

    @Test
    @DisplayName("이달의 추천 리뷰 조회 API")
    void findReviewOfMonth() throws Exception {
        //given
        List<ReviewSimpleResponse> expectedResult = generateReviewSimpleResponse(values().length);
        given(reviewFindService.findReviewOfMonth()).willReturn(expectedResult);

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

    private Slice<ReviewResponse> generateReviewSliceResponse(int count) {
        return new SliceImpl<>(generateReviewResponses(count));
    }

    private List<ReviewResponse> generateReviewResponses(int count) {
        User user = UserFixture.B.toEntity();
        HairStyle hairStyle = HairStyleFixture.A.toEntity();

        List<ReviewResponse> result = new ArrayList<>();
        ReviewFixture[] reviewFixtures = values();

        for (int index = 0; index < count; index++) {
            ReviewFixture fixture = reviewFixtures[index];

            Review review = fixture.toEntity(user, hairStyle);
            result.add(new ReviewResponse(review, true));
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
            result.add(new ReviewSimpleResponse(
                    1L,
                    user.getNicknameValue(),
                    hairStyle.getName(),
                    review.getContents()
            ));
        }

        return result;
    }
}
