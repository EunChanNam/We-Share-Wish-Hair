package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("User-MyPageControllerTest - WebMvcTest")
public class UserInfoControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/user";

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
                    .get(BASE_URL + "/my_page");

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("사용자의 마이페이지 정보를 조회한다")
        void success() throws Exception {
            //given
            MyPageResponse expectedResponse = generateMyPageResponse();
            given(userInfoService.getMyPageInfo(1L)).willReturn(expectedResponse);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/my_page")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //when
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    responseFields(
                                            fieldWithPath("nickname").description("사용자 닉네임"),
                                            fieldWithPath("sex").description("사용자 성별"),
                                            fieldWithPath("point").description("사용자 잔여 포인트"),
                                            fieldWithPath("reviews[].reviewId").description("리뷰 아이디"),
                                            fieldWithPath("reviews[].hairStyleName").description("헤어스타일 이름"),
                                            fieldWithPath("reviews[].userNickname").description("작성자 닉네임"),
                                            fieldWithPath("reviews[].score").description("리뷰 점수"),
                                            fieldWithPath("reviews[].contents").description("리뷰 내용"),
                                            fieldWithPath("reviews[].createdDate").description("리뷰 작성 일"),
                                            fieldWithPath("reviews[].photos").optional().description("사진이 없을 수 있음"),
                                            fieldWithPath("reviews[].photos[].storeUrl").description("리뷰 사진 URI"),
                                            fieldWithPath("reviews[].likes").description("좋아요 수"),
                                            fieldWithPath("reviews[].hashTags[].tag").description("해시 태그"),
                                            fieldWithPath("reviews[].writer").description("작성자 여부")
                                    )
                            )
                    );
        }
    }

    private MyPageResponse generateMyPageResponse() {
        User user = UserFixture.A.toEntity();
        ReflectionTestUtils.setField(user, "id", 1L);

        List<ReviewResponse> reviewResponses = new ArrayList<>();

        for (int index = 0; index < 3; index++) {
            reviewResponses.add(generateReviewResponse(index));
        }
        return new MyPageResponse(user, reviewResponses);
    }

    private ReviewResponse generateReviewResponse(int index) {
        User user = UserFixture.B.toEntity();
        ReflectionTestUtils.setField(user, "id", 2L);
        HairStyle hairStyle = HairStyleFixture.A.toEntity();

        Review review = ReviewFixture.values()[index].toEntity(user, hairStyle);

        ReflectionTestUtils.setField(review, "id", index + 1L);

        return new ReviewResponse(review, user.getId());
    }
}
