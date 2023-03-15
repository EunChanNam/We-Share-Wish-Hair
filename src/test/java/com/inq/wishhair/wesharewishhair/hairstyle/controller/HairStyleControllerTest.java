package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import com.inq.wishhair.wesharewishhair.common.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.common.consts.SessionConst;
import com.inq.wishhair.wesharewishhair.common.utils.UserSessionDtoUtils;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("HairStyleControllerTest - WebMvcTest")
public class HairStyleControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/hair_style/recommend";

    private MockHttpSession session;
    private UserSessionDto sessionDto;

    @BeforeEach
    void setUp() {
        sessionDto = UserSessionDtoUtils.getBSessionDto();
        session = new MockHttpSession();
        session.setAttribute(SessionConst.LONGIN_MEMBER, sessionDto);
    }

    @Test
    @DisplayName("Tag 를 이용해서 헤어스타일 조회")
    void test1() throws Exception {
        //given
        HairStyle a = A.toEntity();
        HairStyle c = C.toEntity();
        HairStyle d = D.toEntity();

        MultiValueMap<String, String> params = getTagParams(A.getTags());
        List<HairStyle> response = new ArrayList<>(List.of(a, c, d));
        Pageable pageable = PageRequest.of(0, 4);
        given(hairStyleService.findRecommendedHairStyle(A.getTags(), sessionDto, pageable))
                .willReturn(response);

        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(BASE_URL)
                .queryParam("size", "4")
                .params(params)
                .session(session);

        //then
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.result").exists(),
                        jsonPath("$.result.size()").value(3),
                        jsonPath("$.contentSize").exists(),
                        jsonPath("$.contentSize").value(3)
                );
    }

    private MultiValueMap<String, String> getTagParams(List<Tag> tags) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("tags", new ArrayList<>(tags.stream()
                .map(Enum::toString).toList()));
        return params;
    }
}
