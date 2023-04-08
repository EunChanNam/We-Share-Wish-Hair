package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.MyPageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("User-MyPageControllerTest - WebMvcTest")
public class MyPageControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/my_page";

    @Nested
    @DisplayName("마이 페이지 조회 API 테스트")
    class getMyPageInfo {
        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 예외를 던진다")
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
        @DisplayName("사용자의 마이페이지 정보를 조회한다")
        void success() throws Exception {
            //given
            MyPageResponse expectedResponse = generateMyPageResponse(3);
            given(myPageService.getMyPageInfo(1L)).willReturn(expectedResponse);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //when
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk());
        }
    }

    private MyPageResponse generateMyPageResponse(int count) {
        User user = UserFixture.A.toEntity();
        List<ReviewResponse> reviewResponses = new ArrayList<>();

        for (int index = 0; index < count; index++) {
            reviewResponses.add(generateReviewResponse());
        }
        return new MyPageResponse(user, reviewResponses);
    }

    private ReviewResponse generateReviewResponse() {
        User user = UserFixture.B.toEntity();
        HairStyle hairStyle = HairStyleFixture.A.toEntity();

        Review review = ReviewFixture.A.toEntity(user, hairStyle);

        return new ReviewResponse(review);
    }
}
