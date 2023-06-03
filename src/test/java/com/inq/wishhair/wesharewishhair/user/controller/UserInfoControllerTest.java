package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.Tag;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import com.inq.wishhair.wesharewishhair.review.infra.query.dto.response.ReviewQueryResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponse;
import com.inq.wishhair.wesharewishhair.review.service.dto.response.ReviewResponseAssembler;
import com.inq.wishhair.wesharewishhair.user.domain.FaceShape;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.MyPageResponse;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.UserInfo;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.UserInformation;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.UserResponseAssembler;
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

    private static final String BASE_URL = "/api/users";

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

    @Nested
    @DisplayName("사용자 정보 조회 API")
    class getUserInformation {
        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 실패")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/info");

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("사용자의 정보를 조회한다")
        void success() throws Exception {
            //given
            UserInformation expectedResponse = generateUserInformation();
            given(userInfoService.getUserInformation(1L))
                    .willReturn(expectedResponse);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/info")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    responseFields(
                                            fieldWithPath("email").description("이메일"),
                                            fieldWithPath("name").description("사용자 이름"),
                                            fieldWithPath("nickname").description("사용자 닉네임"),
                                            fieldWithPath("sex").description("사용자 성별")
                                    )
                            )
                    );
        }
    }

    @Nested
    @DisplayName("홈화면 사용자 정보 조회 API")
    class getUserInfo {
        @Test
        @DisplayName("홈화면에 필요한 사용자 정보를 조회한다")
        void success() throws Exception {
            //given
            User user = UserFixture.A.toEntity();
            user.updateFaceShape(new FaceShape(Tag.OBLONG));
            UserInfo userInfo = new UserInfo(user);

            given(userInfoService.getUserInfo(1L)).willReturn(userInfo);

            //then
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/home_info")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    responseFields(
                                            fieldWithPath("nickname").description("사용자 닉네임"),
                                            fieldWithPath("hasFaceShape").description("얼굴형 보유 여부"),
                                            fieldWithPath("faceShapeTag").description("사용자 얼굴형, 얼굴형을 보유하지 않을 시 null")
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
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/home_info");

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }
    }

    private UserInformation generateUserInformation() {
        User user = UserFixture.A.toEntity();
        user.updateFaceShape(new FaceShape(Tag.ROUND));

        return UserResponseAssembler.toUserInformation(user);
    }

    private MyPageResponse generateMyPageResponse() {
        User user = UserFixture.A.toEntity();

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

        ReviewQueryResponse queryResponse = new ReviewQueryResponse(review, index + 10L);
        ReviewResponse reviewResponse = ReviewResponseAssembler.toReviewResponse(queryResponse);
        reviewResponse.addIsWriter(1L);
        return reviewResponse;
    }
}
