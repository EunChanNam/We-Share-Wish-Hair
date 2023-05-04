package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.Paging;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.utils.PageableGenerator;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture.*;
import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("HairStyleControllerTest - WebMvcTest")
public class HairStyleSearchControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/hair_style";

    @Nested
    @DisplayName("메인 헤어 추천 API")
    class recommendHairStyle {
        @Test
        @DisplayName("헤더에 Access 토큰을 포함하지 않아서 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;
            MultiValueMap<String, String> params = generateTagParams(A.getTags());

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/recommend")
                    .queryParams(params);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("입력된 태그와 userId로 조회된 헤어스타일에 대한 응답을 받는다")
        void success() throws Exception {
            //given
            List<Tag> tags = E.getTags();
            ResponseWrapper<HairStyleResponse> expectedResponse = assembleWrappedResponse(List.of(E, C, D));

            given(hairStyleSearchService.recommendHair(tags, 1L))
                    .willReturn(expectedResponse);

            MultiValueMap<String, String> params = generateTagParams(tags);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/recommend")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .queryParams(params);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists()
                    ).andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    requestParameters(
                                            parameterWithName("tags").description("검색 Tag")
                                                    .attributes(constraint("하나 이상 필요"))
                                    ),
                                    hairStyleResponseDocument()
                            )
                    );
        }

        @Test
        @DisplayName("얼굴형 데이터가 없는 사용자로 실패한다")
        void failByNoFaceShape() throws Exception {
            //given
            List<Tag> tags = List.of(Tag.PERM, Tag.LONG);
            MultiValueMap<String, String> params = generateTagParams(tags);

            ErrorCode expectedError = ErrorCode.USER_NO_FACE_SHAPE_TAG;
            given(hairStyleSearchService.recommendHair(tags, 1L))
                    .willThrow(new WishHairException(expectedError));

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/recommend")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .queryParams(params);

            //then
            assertException(expectedError, requestBuilder, status().isForbidden());
        }

        @Test
        @DisplayName("태그를 입력하지 않으면 400 예외를 던진다")
        void failByNoTag() throws Exception {
            //given, 태그 X
            ErrorCode expectedError = ErrorCode.RUN_NOT_ENOUGH_TAG;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/recommend")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            assertException(expectedError, requestBuilder, status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("사용자 얼굴형 맞춤 헤어 추천 API")
    class recommendHairStyleByFaceShape {

        @Test
        @DisplayName("헤더에 Access 토큰을 포함하지 않아서 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;

            //when

            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/home");
            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }

        @Test
        @DisplayName("사용자 얼굴형으로 추천된 헤어스타일을 조회한다")
        void success1() throws Exception {
            //given
            given(hairStyleSearchService.recommendHairByFaceShape(1L))
                    .willReturn(assembleWrappedResponse(List.of(C, E, D)));

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/home")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$").exists()
                    ).andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    hairStyleResponseDocument()
                            )
                    );
        }
    }

    @Nested
    @DisplayName("찜한 헤어스타일 조회 API")
    class findWishHairStyles {
        @Test
        @DisplayName("찜한 헤어스타일을 생성된 순으로 조회한다")
        void success() throws Exception {
            //given
            given(hairStyleSearchService.findWishHairStyles(any(), any()))
                    .willReturn(assemblePagedResponse(List.of(A, B, C)));

            //when
            MultiValueMap<String, String> pageableParams = generatePageableParams(PageableGenerator.generateSimplePageable(10));
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(BASE_URL + "/wish")
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .params(pageableParams);

            //then
            mockMvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    pageableParametersDocument(10, null),
                                    pagedHairStyleResponseDocument()
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
                    .get(BASE_URL + "/wish");

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
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

    private PagedResponse<HairStyleResponse> assemblePagedResponse(List<HairStyleFixture> fixtures) {
        Paging defualtPaging = new Paging(4, 0, false);
        return new PagedResponse<>(generateExpectedResponse(fixtures), defualtPaging);
    }

    private List<HairStyleResponse> generateExpectedResponse(List<HairStyleFixture> fixtures) {
        List<HairStyleResponse> expectedResponse = new ArrayList<>();
        long id = 1L;
        for (HairStyleFixture fixture : fixtures) {
            expectedResponse.add(fixture.toResponse(id++));
        }
        return expectedResponse;
    }

    private ResponseFieldsSnippet hairStyleResponseDocument() {
        return responseFields(
                fieldWithPath("result[].hairStyleId").description("헤어스타일 ID(PK)"),
                fieldWithPath("result[].name").description("헤어스타일 이름"),
                fieldWithPath("result[].photos[].storeUrl").description("사진 URI 리소스"),
                fieldWithPath("result[].hashTags[].tag").description("헤어스타일 해시태그")
        );
    }

    private ResponseFieldsSnippet pagedHairStyleResponseDocument() {
        return hairStyleResponseDocument().and(
                fieldWithPath("paging.contentSize").description("조회된 리뷰 개수"),
                fieldWithPath("paging.page").description("현재 페이지"),
                fieldWithPath("paging.hasNext").description("다음 페이지 존재 여부")
        );
    }
}
