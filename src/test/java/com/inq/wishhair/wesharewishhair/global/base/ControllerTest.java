package com.inq.wishhair.wesharewishhair.global.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inq.wishhair.wesharewishhair.auth.controller.AuthController;
import com.inq.wishhair.wesharewishhair.auth.controller.TokenReissueController;
import com.inq.wishhair.wesharewishhair.auth.service.AuthService;
import com.inq.wishhair.wesharewishhair.auth.service.TokenReissueService;
import com.inq.wishhair.wesharewishhair.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.hairstyle.service.HairStyleService;
import com.inq.wishhair.wesharewishhair.user.controller.MailController;
import com.inq.wishhair.wesharewishhair.user.service.MailSendService;
import com.inq.wishhair.wesharewishhair.user.service.PointService;
import com.inq.wishhair.wesharewishhair.user.service.UserService;
import com.inq.wishhair.wesharewishhair.hairstyle.controller.HairStyleController;
import com.inq.wishhair.wesharewishhair.user.controller.UserController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value =
        {UserController.class, HairStyleController.class, AuthController.class, TokenReissueController.class,
        HairStyleController.class, MailController.class})
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @MockBean
    protected HairStyleService hairStyleService;

    @MockBean
    protected PointService pointService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected JwtTokenProvider provider;

    @MockBean
    protected TokenReissueService tokenReissueService;

    @MockBean
    protected MailSendService mailSendService;

    @MockBean
    protected HttpServletRequest httpServletRequest;

    public String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
