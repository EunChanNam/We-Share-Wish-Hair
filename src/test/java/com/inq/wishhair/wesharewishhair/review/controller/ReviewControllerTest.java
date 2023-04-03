package com.inq.wishhair.wesharewishhair.review.controller;

import com.inq.wishhair.wesharewishhair.fixture.ReviewFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.review.controller.dto.request.ReviewCreateRequest;
import com.inq.wishhair.wesharewishhair.review.controller.utils.ReviewCreateRequestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ReviewControllerTest - WebMvcTest")
public class ReviewControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/review";

    @BeforeEach
    void setUp() {
        //given
        given(provider.isValidToken(ACCESS_TOKEN)).willReturn(true);
        given(provider.getId(ACCESS_TOKEN)).willReturn(1L);
    }

    @SneakyThrows
    @Test
    @DisplayName("리뷰 작성 API 테스트")
    void createReview() {
        //given
        ReviewCreateRequest request = ReviewCreateRequestUtils.createRequest(ReviewFixture.A, 1L);
        given(reviewService.createReview(request, 1L)).willReturn(1L);

        MultiValueMap<String, String> parms = new LinkedMultiValueMap<>();
        generateParams(request, parms);

        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .multipart(BASE_URL)
                .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                .params(parms);

        //then
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$").exists(),
                        jsonPath("$.success").value(true)
                );
    }

    @Test
    @DisplayName("리뷰 삭제 API 테스트")
    void deleteReview() throws Exception {
        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(BASE_URL + "/1")
                .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

        //then
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").exists(),
                        jsonPath("$.success").value(true)
                );
    }

    private void generateParams(ReviewCreateRequest request, MultiValueMap<String, String> parms) {
        parms.add("contents", request.getContents());
        parms.add("score", "4.5");
        parms.add("hairStyleId", "1");
    }
}
