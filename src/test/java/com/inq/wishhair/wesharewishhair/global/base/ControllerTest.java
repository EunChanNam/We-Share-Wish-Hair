package com.inq.wishhair.wesharewishhair.global.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inq.wishhair.wesharewishhair.auth.controller.AuthController;
import com.inq.wishhair.wesharewishhair.auth.controller.TokenReissueController;
import com.inq.wishhair.wesharewishhair.auth.service.AuthService;
import com.inq.wishhair.wesharewishhair.auth.service.TokenReissueService;
import com.inq.wishhair.wesharewishhair.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.global.config.RestDocsConfig;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.hairstyle.service.HairStyleSearchService;
import com.inq.wishhair.wesharewishhair.review.controller.LikeReviewController;
import com.inq.wishhair.wesharewishhair.review.controller.ReviewController;
import com.inq.wishhair.wesharewishhair.review.controller.ReviewSearchController;
import com.inq.wishhair.wesharewishhair.review.service.LikeReviewService;
import com.inq.wishhair.wesharewishhair.review.service.ReviewSearchService;
import com.inq.wishhair.wesharewishhair.review.service.ReviewService;
import com.inq.wishhair.wesharewishhair.user.controller.*;
import com.inq.wishhair.wesharewishhair.user.service.*;
import com.inq.wishhair.wesharewishhair.hairstyle.controller.HairStyleController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.RequestParametersSnippet;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.ACCESS_TOKEN;
import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.AUTHORIZATION;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value =
        {UserController.class, HairStyleController.class, AuthController.class, TokenReissueController.class,
        HairStyleController.class, MailController.class, ReviewController.class, ReviewSearchController.class,
        LikeReviewController.class, MyPageController.class, PointController.class, PointSearchController.class})
@ExtendWith(RestDocumentationExtension.class)
@Import(RestDocsConfig.class)
@AutoConfigureRestDocs
public abstract class ControllerTest {

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @MockBean
    protected HairStyleSearchService hairStyleSearchService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected JwtTokenProvider provider;

    @MockBean
    protected TokenReissueService tokenReissueService;

    @MockBean
    protected MailSendService mailSendService;

    @MockBean
    protected ReviewService reviewService;

    @MockBean
    protected ReviewSearchService reviewSearchService;

    @MockBean
    protected LikeReviewService likeReviewService;

    @MockBean
    protected MyPageService myPageService;

    @MockBean
    protected PointService pointService;

    @MockBean
    protected PointSearchService pointSearchService;


    protected String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    @BeforeEach
    void setUpLogin() {
        //given
        given(provider.isValidToken(ACCESS_TOKEN)).willReturn(true);
        given(provider.getId(ACCESS_TOKEN)).willReturn(1L);
    }

    @BeforeEach
    void setUp(final WebApplicationContext context,
               final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))  // rest docs 설정 주입
                .alwaysDo(print()) // andDo(print()) 코드 포함
                .alwaysDo(restDocs)
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
                .build();
    }

    protected Attributes.Attribute constraint(String value) {
        return new Attributes.Attribute("constraints", value);
    }

    protected ResponseFieldsSnippet successResponseDocument() {
        return PayloadDocumentation.responseFields(
                fieldWithPath("success").description("성공 여부")
        );
    }

    protected RequestHeadersSnippet accessTokenHeaderDocument() {
        return requestHeaders(
                headerWithName(AUTHORIZATION).description("Bearer + Access Token")
        );
    }

    protected void assertException(ErrorCode expectedError,
                                 MockHttpServletRequestBuilder requestBuilder,
                                 ResultMatcher status) throws Exception {

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status,
                        jsonPath("$").exists(),
                        jsonPath("$.message").value(expectedError.getMessage())
                ).andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("status").description("Http 상태 코드"),
                                        fieldWithPath("code").description("예외 코드"),
                                        fieldWithPath("message").description("예외 메세지")
                                )
                        )
                );
    }

    protected RequestParametersSnippet pageableParametersDocument(
            int defaultSize, String defaultSort
    ) {
        RequestParametersSnippet result = requestParameters(
                parameterWithName("size").description("페이지 사이즈(한번에 가져오는 개수)")
                        .attributes(constraint("default : " + defaultSize)),
                parameterWithName("page").description("페이지 번호")
                        .attributes(constraint("첫 페이지는 0, default : 0"))
        );
        if (defaultSort != null) {
            result.and(parameterWithName("sort").description("정렬 조건 정렬변수.direction")
                    .attributes(constraint("첫 정렬 조건은 반드시 " + defaultSort + " default : " + defaultSort)));
        }
        return result;
    }

    protected void assertSuccess(MockHttpServletRequestBuilder requestBuilder, ResultMatcher status) throws Exception {
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status,
                        jsonPath("$").exists(),
                        jsonPath("$.success").value(true)
                );
    }

    protected MultiValueMap<String, String> generatePageableParams(Pageable pageable) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        if (!pageable.getSort().isEmpty()) {
            String[] splitSort = pageable.getSort().toString().split(":");
            String variable = splitSort[0];
            String direction = splitSort[1].trim();

            params.add("sort", variable + "." + direction);
        }

        params.add("size", String.valueOf(pageable.getPageSize()));
        params.add("page", String.valueOf(pageable.getPageNumber()));
        return params;
    }
}
