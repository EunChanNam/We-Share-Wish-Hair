package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("hairStyle-WishHairControllerTest - WebMvcTest")
public class WishHairControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/hair_style/wish/";

    @Nested
    @DisplayName("찜하기 API")
    class executeWish {
        @Test
        @DisplayName("찜하기에 성공한다")
        void success() throws Exception {
            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .post(BASE_URL + "/{hairStyleId}", 1L)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    pathParameters(
                                            parameterWithName("hairStyleId").description("찜할 헤어스타일 ID")
                                    ),
                                    successResponseDocument()
                            )
                    );
        }

        @Test
        @DisplayName("이미 찜한 헤어스타일에 요청을 해 실패한다")
        void failByAlreadyExist() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.WISH_HAIR_ALREADY_EXIST;
            doThrow(new WishHairException(expectedError))
                    .when(wishHairService).executeWish(any(), any());

            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .post(BASE_URL + "/{hairStyleId}", 1L)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            assertException(expectedError, requestBuilder, status().isBadRequest());
        }

        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 실패한다")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .post(BASE_URL + "/{hairStyleId}", 1L);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }
    }
}
