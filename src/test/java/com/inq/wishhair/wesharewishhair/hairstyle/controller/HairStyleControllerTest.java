package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import com.inq.wishhair.wesharewishhair.common.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.common.consts.SessionConst;
import com.inq.wishhair.wesharewishhair.common.utils.UserSessionDtoUtils;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.HairStyle;
import com.inq.wishhair.wesharewishhair.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.fixture.HairStyleFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("HairStyleControllerTest - WebMvcTest")
public class HairStyleControllerTest extends ControllerTest {

    private static final String BASE_URL = "/api/hair_style/recommend";
    private static final HairStyle A = HairStyleFixture.A.toEntity();
    private MockHttpSession session;
    private UserSessionDto sessionDto;

    @BeforeEach
    void setUp() {
        sessionDto = UserSessionDtoUtils.getASessionDto();
        session = new MockHttpSession();
        session.setAttribute(SessionConst.LONGIN_MEMBER, sessionDto);
    }

    @Test
    @DisplayName("Tag 를 이용해서 헤어스타일 조회")
    void test1() throws Exception {
        //given
        List<Tag> tags = HairStyleFixture.A.getTags();
        MultiValueMap<String, String> params = getAParams(tags);
        List<HairStyle> response = new ArrayList<>(List.of(A));
        given(hairStyleService.findRecommendedHairStyle(tags, sessionDto, null))
                .willReturn(response);

        //when
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(BASE_URL)
                .params(params)
                .session(session);

        //then
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.size()").value(1),
                        jsonPath("$[0].name").value(A.getName()),
                        jsonPath("$[0].photos.size()").value(4)
                );
    }

    private MultiValueMap<String, String> getAParams(List<Tag> tags) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("tags", new ArrayList<>(tags.stream()
                .map(Enum::toString).toList()));
        return params;
    }
}
