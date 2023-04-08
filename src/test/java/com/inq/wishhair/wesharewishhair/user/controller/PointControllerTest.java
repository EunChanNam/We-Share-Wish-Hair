package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.utils.TokenUtils;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PointUseRequest;
import com.inq.wishhair.wesharewishhair.user.controller.utils.PointUseRequestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("User-PointControllerTest - WebMvcTest")
public class PointControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/point";

    @Nested
    @DisplayName("포인트 사용 API 테스트")
    class userPoint {
        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            PointUseRequest request = PointUseRequestUtils.request(1000);
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(BASE_URL + "/use")
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("포인트 사용에 성공한다")
        void success() throws Exception {
            //given
            PointUseRequest request = PointUseRequestUtils.request(1000);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .post(BASE_URL + "/use")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .content(toJson(request))
                    .contentType(APPLICATION_JSON);

            //then
            assertSuccess(requestBuilder, status().isOk());
        }
    }
}
