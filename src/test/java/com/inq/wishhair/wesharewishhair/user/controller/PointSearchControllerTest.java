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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("User-PointSearchControllerTest - WebMvcTest")
public class PointSearchControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/users/point";

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
            Pageable pageable = DefaultPageableUtils.getDefualtPageable();
            given(pointSearchService.findPointHistories(any(), any()))
                    .willReturn(assemblePagedResponse(values().length));

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .queryParams(generatePageableParams(pageable));

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    pageableParametersDocument(10, null),
                                    responseFields(
                                            fieldWithPath("result[]").optional().description("포인트 내역이 없을 수 있음"),
                                            fieldWithPath("result[].pointType").description("포인트 타입"),
                                            fieldWithPath("result[].dealAmount").description("거래 량"),
                                            fieldWithPath("result[].point").description("잔여 포인트"),
                                            fieldWithPath("result[].dealDate").description("거래 날짜"),

                                            fieldWithPath("paging.contentSize").description("조회된 내역 개수"),
                                            fieldWithPath("paging.page").description("현재 페이지"),
                                            fieldWithPath("paging.hasNext").description("다음 페이지 존재 여부")
                                    )
                            )
                    );
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
