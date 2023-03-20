package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import com.inq.wishhair.wesharewishhair.global.base.ControllerTest;
import com.inq.wishhair.wesharewishhair.global.utils.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.BDDMockito;

import static com.inq.wishhair.wesharewishhair.global.utils.TokenUtils.ACCESS_TOKEN;
import static org.mockito.BDDMockito.given;

@DisplayName("HairStyleControllerTest - WebMvcTest")
public class HairStyleControllerTest extends ControllerTest {

    @BeforeEach
    void setUp() {
        given(provider.isValidToken(ACCESS_TOKEN)).willReturn(true);
        given(provider.getId(ACCESS_TOKEN)).willReturn(1L);
    }
}
