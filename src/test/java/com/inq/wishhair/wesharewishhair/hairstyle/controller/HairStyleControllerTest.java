package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.global.fixture.HairStyleFixture;
import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
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
import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.getDefaultPageable;
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
public class HairStyleControllerTest extends ControllerTest {

    private static final String RECOMMEND_URL = "/api/hair_style/recommend";
    private static final String FACE_RECOMMEND_URL = "/api/hair_style/home";

    @Nested
    @DisplayName("헤어 추천 API")
    class recommendHairStyle {
        @Test
        @DisplayName("헤더에 Access 토큰을 포함하지 않아서 예외를 던진다")
        void failByNoAccessToken() throws Exception {
            //given
            ErrorCode expectedError = ErrorCode.AUTH_REQUIRED_LOGIN;
            MultiValueMap<String, String> params = generateTagParams(A.getTags());

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(RECOMMEND_URL)
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

            given(hairStyleSearchService.findRecommendedHairStyle(tags, 1L))
                    .willReturn(expectedResponse);

            MultiValueMap<String, String> params = generateTagParams(tags);

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(RECOMMEND_URL)
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
                                                    .attributes(constraint("얼굴형 태그 포함 하나 이상 필요"))
                                    ),
                                    hairStyleResponseDocument()
                            )
                    );
        }

        @Test
        @DisplayName("태그를 입력하지 않으면 400 예외를 던진다")
        void failByNoTag() throws Exception {
            //given, 태그 X
            ErrorCode expectedError = ErrorCode.RUN_NOT_ENOUGH_TAG;

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(RECOMMEND_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN);

            //then
            assertException(expectedError, requestBuilder, status().isBadRequest());
        }

        @Test
        @DisplayName("태그에 얼굴형 태그가 없으면 400 예외를 던진다")
        void failByNoFaceShapeTag() throws Exception{
            //given
            List<Tag> tags = new ArrayList<>(E.getTags());
            tags.removeIf(Tag::isFaceShapeType);
            MultiValueMap<String, String> params = generateTagParams(tags);

            ErrorCode expectedError = ErrorCode.RUN_NO_FACE_SHAPE_TAG;
            given(hairStyleSearchService.findRecommendedHairStyle(any(), any()))
                    .willThrow(new WishHairException(expectedError));

            //when
            MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                    .get(RECOMMEND_URL)
                    .header(AUTHORIZATION, BEARER + ACCESS_TOKEN)
                    .queryParams(params);

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
                    .get(RECOMMEND_URL);

            //then
            assertException(expectedError, requestBuilder, status().isUnauthorized());
        }
        @Test
        @DisplayName("사용자 얼굴형 기반 헤어추천 서비스 로직의 결과를 헤어스타일 응답으로 변환해 응답한다")
        void success() throws Exception {
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
                    ).andDo(
                            restDocs.document(
                                    accessTokenHeaderDocument(),
                                    hairStyleResponseDocument()
                            )
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

    private ResponseFieldsSnippet hairStyleResponseDocument() {
        return responseFields(
                fieldWithPath("result[].hairStyleId").description("헤어스타일 ID(PK)"),
                fieldWithPath("result[].name").description("헤어스타일 이름"),
                fieldWithPath("result[].photos[].resource").description("사진 URI 리소스"),
                fieldWithPath("result[].hashTags[].tag").description("헤어스타일 해시태그")
        );
    }
}
