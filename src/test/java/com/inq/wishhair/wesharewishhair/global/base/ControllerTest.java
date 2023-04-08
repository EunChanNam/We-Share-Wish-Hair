package com.inq.wishhair.wesharewishhair.global.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inq.wishhair.wesharewishhair.auth.controller.AuthController;
import com.inq.wishhair.wesharewishhair.auth.controller.TokenReissueController;
import com.inq.wishhair.wesharewishhair.auth.service.AuthService;
import com.inq.wishhair.wesharewishhair.auth.service.TokenReissueService;
import com.inq.wishhair.wesharewishhair.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.hairstyle.service.HairStyleSearchService;
import com.inq.wishhair.wesharewishhair.review.controller.LikeReviewController;
import com.inq.wishhair.wesharewishhair.review.controller.ReviewController;
import com.inq.wishhair.wesharewishhair.review.controller.ReviewSearchController;
import com.inq.wishhair.wesharewishhair.review.service.LikeReviewService;
import com.inq.wishhair.wesharewishhair.review.service.ReviewSearchService;
import com.inq.wishhair.wesharewishhair.review.service.ReviewService;
import com.inq.wishhair.wesharewishhair.user.controller.*;
import com.inq.wishhair.wesharewishhair.user.service.*;
import com.inq.wishhair.wesharewishhair.hairstyle.controller.HairStyleController;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.ACCESS_TOKEN;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value =
        {UserController.class, HairStyleController.class, AuthController.class, TokenReissueController.class,
        HairStyleController.class, MailController.class, ReviewController.class, ReviewSearchController.class,
        LikeReviewController.class, MyPageController.class, PointController.class, PointSearchController.class})
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @MockBean
    protected HairStyleSearchService hairStyleSearchService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected JwtTokenProvider provider;

    @MockBean
    protected TokenReissueService tokenReissueService;

    @MockBean
    protected MailSendService mailSendService;

    @MockBean
    protected ReviewService reviewService;

    @MockBean
    protected ReviewSearchService reviewSearchService;

    @MockBean
    protected LikeReviewService likeReviewService;

    @MockBean
    protected MyPageService myPageService;

    @MockBean
    protected PointService pointService;

    @MockBean
    protected PointSearchService pointSearchService;


    protected String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    @BeforeEach
    void setUp() {
        //given
        given(provider.isValidToken(ACCESS_TOKEN)).willReturn(true);
        given(provider.getId(ACCESS_TOKEN)).willReturn(1L);
    }

    protected void assertException(ErrorCode expectedError,
                                 MockHttpServletRequestBuilder requestBuilder,
                                 ResultMatcher status) throws Exception {

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status,
                        jsonPath("$").exists(),
                        jsonPath("$.message").value(expectedError.getMessage())
                );
    }

    protected void assertSuccess(MockHttpServletRequestBuilder requestBuilder, ResultMatcher status) throws Exception {
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status,
                        jsonPath("$").exists(),
                        jsonPath("$.success").value(true)
                );
    }
}
