package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import org.junit.jupiter.api.BeforeEach;
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
import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.ACCESS_TOKEN;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("HairStyleControllerTest - WebMvcTest")
public class HairStyleControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/hair_style/recommend";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";

    @BeforeEach
    void setUp() {
        given(provider.isValidToken(ACCESS_TOKEN)).willReturn(true);
        given(provider.getId(ACCESS_TOKEN)).willReturn(1L);
    }

    @Nested
    @DisplayName("헤어 추천 API")
    class respondRecommendedHairStyle {
        @Test
        @DisplayName("입력된 태그와 userId로 조회된 헤어스타일에 대한 응답을 받는다")
        void test1() throws Exception {
            //given
            List<Tag> tags = A.getTags();
            List<HairStyleResponse> expectedResponse = generateExpectedResponse(List.of(A, C, D, E));

            given(hairStyleService.findRecommendedHairStyle(tags, 1L, getDefaultPageable()))
                    .willReturn(expectedResponse);

            MultiValueMap<String, String> params = generateTagParams(tags);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL)
                    .header(AUTHORIZATION, BEARER +  ACCESS_TOKEN)
                    .queryParams(params);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists(),
                            jsonPath("$.contentSize").value(4),
                            jsonPath("$.result.size()").value(4),
                            jsonPath("$.result.[0].hairStyleId").value(1L),
                            jsonPath("$.result.[0].name").value(A.getName()),
                            jsonPath("$.result.[0].photos.size()").value(4)
                    );
        }
    }

    private MultiValueMap<String, String> generateTagParams(List<Tag> tags) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        tags.forEach(tag -> queryParams.add("tags", tag.toString()));
        return queryParams;
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
