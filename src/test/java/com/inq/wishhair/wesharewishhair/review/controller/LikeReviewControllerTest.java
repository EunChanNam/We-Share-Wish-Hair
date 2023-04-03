package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("LikeReviewControllerTest - WebMvcTest")
public class LikeReviewControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/review/like";

    @Test
    @DisplayName("좋아요 API 테스트")
    void likeReview() throws Exception {
        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(BASE_URL + "/1")
                .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

        //then
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").exists(),
                        jsonPath("$.success").value(true)
                );
    }
}
