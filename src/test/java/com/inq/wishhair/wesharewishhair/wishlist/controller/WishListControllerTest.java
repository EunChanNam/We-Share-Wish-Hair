package com.inq.wishhair.wesharewishhair.wishlist.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("WishListControllerTest - WebMvcTest")
public class WishListControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/wish_list";

    @Nested
    @DisplayName("찜 목록 생성 API")
    class createWishList {
        @Test
        @DisplayName("찜 목록을 생성한다")
        void success() throws Exception {
            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .post(BASE_URL + "/{hairStyleId}", 1L)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isCreated())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    pathParameters(
                                            parameterWithName("hairStyleId").description("찜 할 헤어스타일 아이디")
                                    ),
                                    successResponseDocument()
                            )
                    );
        }

        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 실패")
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
