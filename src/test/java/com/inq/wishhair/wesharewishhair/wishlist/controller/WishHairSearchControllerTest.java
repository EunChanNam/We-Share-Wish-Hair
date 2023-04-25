package com.inq.wishhair.wesharewishhair.wishlist.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.Paging;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.WishListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.getDefaultPageable;
import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("WishListSearchControllerTest - WebMvcTest")
public class WishHairSearchControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/wish_list";

    @Nested
    @DisplayName("찜 목록 조회 API")
    class getWishHair {
        @Test
        @DisplayName("찜 목록을 조회한다")
        void success() throws Exception {
            //given
            given(wishHairSearchService.findWishList(any(), any()))
                    .willReturn(generatePagedResponse(4));

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .queryParams(generatePageableParams(getDefaultPageable()));

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    pageableParametersDocument(4, null),
                                    responseFields(
                                            fieldWithPath("result[].hairStyleId").description("헤어스타일 아이디"),
                                            fieldWithPath("result[].hairStyleName").description("헤어스타일 이름"),
                                            fieldWithPath("result[].photos[].storeUrl").description("헤어스타일 사진 URL"),
                                            fieldWithPath("result[].hashTags[].tag").description("헤어스타일 해시태그"),

                                            fieldWithPath("paging.contentSize").description("조회된 찜 목록 개수"),
                                            fieldWithPath("paging.page").description("현재 페이지"),
                                            fieldWithPath("paging.hasNext").description("다음 페이지 존재 여부")
                                    )
                            )
                    );
        }

        @Test
        @DisplayName("헤더에 토큰을 포함하지 않아 실패")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL);

            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }
    }

    private PagedResponse<WishListResponse> generatePagedResponse(int count) {
        Paging defaultPaging = new Paging(count, 0, false);
        return new PagedResponse<>(generateWishListResponses(count), defaultPaging);
    }

    private List<WishListResponse> generateWishListResponses(int count) {
        HairStyleFixture[] fixtures = HairStyleFixture.values();
        int maxLength = fixtures.length;
        if (count > maxLength) {
            throw new IllegalStateException();
        }

        List<WishListResponse> wishListResponses = new ArrayList<>();

        for (int index = 0; index < count; index++) {
            HairStyle hairStyle = fixtures[index].toEntity();
            ReflectionTestUtils.setField(hairStyle, "id", 1L + index);

            wishListResponses.add(new WishListResponse(hairStyle));
        }

        return wishListResponses;
    }
}
