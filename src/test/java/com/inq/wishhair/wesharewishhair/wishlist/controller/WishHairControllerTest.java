package com.inq.wishhair.wesharewishhair.wishlist.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("WishListControllerTest - WebMvcTest")
public class WishHairControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/wish_list";

    @Nested
    @DisplayName("찜 목록 생성 API")
    class createWishHair {
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

    @Nested
    @DisplayName("찜 목록 삭제 API")
    class deleteWishHair {
        @Test
        @DisplayName("찜 목록을 삭제한다")
        void success() throws Exception {
            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .delete(BASE_URL + "/{wishListId}", 1L)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    pathParameters(
                                            parameterWithName("wishListId").description("삭제할 찜 목록 아이디")
                                    ),
                                    successResponseDocument()
                            )
                    );
        }

        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 실패한다")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .delete(BASE_URL + "/{wishListId}", 1L);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("찜 목록 소유자가 아니라서 실패한다")
        void failByNotHost() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.WISH_LIST_NOT_HOST;
            doThrow(new WishHairException(expectedError)).when(wishHairService).deleteWishList(1L, 1L);

            //when
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                    .delete(BASE_URL + "/{wishListId}", 1L)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            assertException(expectedError, requestBuilder, status().isForbidden());
        }
    }
}
