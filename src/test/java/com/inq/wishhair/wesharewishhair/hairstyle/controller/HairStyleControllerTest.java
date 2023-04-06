package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture.*;
import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.getDefaultPageable;
import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("HairStyleControllerTest - WebMvcTest")
public class HairStyleControllerTest extends ControllerTest {

    private static final String RECOMMEND_URL = "/api/hair_style/recommend";
    private static final String FACE_RECOMMEND_URL = "/api/hair_style/home";

    @Nested
    @DisplayName("헤어 추천 API")
    class respondRecommendedHairStyle {
        @Test
        @DisplayName("입력된 태그와 userId로 조회된 헤어스타일에 대한 응답을 받는다")
        void test1() throws Exception {
            //given
            List<Tag> tags = A.getTags();
            ResponseWrapper<HairStyleResponse> expectedResponse = assembleWrappedResponse(List.of(A, C, D, E));

            given(hairStyleSearchService.findRecommendedHairStyle(tags, 1L, getDefaultPageable()))
                    .willReturn(expectedResponse);

            MultiValueMap<String, String> params = generateTagParams(tags);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(RECOMMEND_URL)
                    .header(AUTHORIZATION, BEARER +  ACCESS_TOKEN)
                    .queryParams(params);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists()
                    );
        }

        @Test
        @DisplayName("태그를 입력하지 않으면 400 예외를 던진다")
        void test2() throws Exception {
            //given, 태그 X
            ErrorCode expectedError = ErrorCode.RUN_NOT_ENOUGH_TAG;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(RECOMMEND_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isBadRequest(),
                            jsonPath("$").exists(),
                            jsonPath("$.message").value(expectedError.getMessage())
                    );
        }
    }

    @Nested
    @DisplayName("사용자 얼굴형 맞춤 헤어 추천 API")
    class findHairStyleByFaceShape {
        @Test
        @DisplayName("사용자 얼굴형 기반 헤어추천 서비스 로직의 결과를 헤어스타일 응답으로 변환해 응답한다")
        void test3() throws Exception {
            //given
            given(hairStyleSearchService.findHairStyleByFaceShape(1L))
                    .willReturn(assembleWrappedResponse(List.of(C, E, D)));

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(FACE_RECOMMEND_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists()
                    );
        }
    }

    private MultiValueMap<String, String> generateTagParams(List<Tag> tags) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        tags.forEach(tag -> queryParams.add("tags", tag.toString()));
        return queryParams;
    }

    private ResponseWrapper<HairStyleResponse> assembleWrappedResponse(List<HairStyleFixture> fixtures) {
        return new ResponseWrapper<>(generateExpectedResponse(fixtures));
    }

    private List<HairStyleResponse> generateExpectedResponse(List<HairStyleFixture> fixtures) {
        List<HairStyleResponse> expectedResponse = new ArrayList<>();
        long id = 1L;
        for (HairStyleFixture fixture : fixtures) {
            expectedResponse.add(fixture.toResponse(id++));
        }
        return expectedResponse;
    }
}
