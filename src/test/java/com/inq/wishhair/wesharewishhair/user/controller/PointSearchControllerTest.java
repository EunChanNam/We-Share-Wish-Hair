package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.global.fixture.PointFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.Paging;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.utils.DefaultPageableUtils;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.PointResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.PointFixture.values;
import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("User-PointSearchControllerTest - WebMvcTest")
public class PointSearchControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/point";

    @Nested
    @DisplayName("포인트 내역 조회 API 테스트")
    class findPointHistories {
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
        @DisplayName("사용자의 포인트 내역을 조회한다")
        void success() throws Exception {
            //given
            given(pointSearchService.findPointHistories(1L))
                    .willReturn(assemblePagedResponse(values().length));

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk());
        }
    }

    private PagedResponse<PointResponse> assemblePagedResponse(int count) {
        Paging defaultPaging = new Paging(count, 0, false);
        return new PagedResponse<>(generatePointResponse(count), defaultPaging);
    }

    private List<PointResponse> generatePointResponse(int count) {
        List<PointResponse> responses = new ArrayList<>();
        PointFixture[] fixtures = PointFixture.values();

        for (int i = 0; i < count; i++) {
            PointFixture fixture = fixtures[i];
            LocalDateTime time = LocalDateTime.now().plusDays(i);

            responses.add(new PointResponse(
                    fixture.getPointType().getDescription(),
                    fixture.getDealAmount(),
                    fixture.getPoint(),
                    time
            ));
        }

        return responses;
    }
}
