package com.inq.wishhair.wesharewishhair.global.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inq.wishhair.wesharewishhair.hairstyle.service.HairStyleService;
import com.inq.wishhair.wesharewishhair.domain.login.LoginService;
import com.inq.wishhair.wesharewishhair.user.service.UserPointService;
import com.inq.wishhair.wesharewishhair.user.service.UserService;
import com.inq.wishhair.wesharewishhair.hairstyle.controller.HairStyleController;
import com.inq.wishhair.wesharewishhair.web.login.LoginController;
import com.inq.wishhair.wesharewishhair.user.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({UserController.class, HairStyleController.class, LoginController.class})
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
    protected UserPointService pointService;

    @MockBean
    protected LoginService loginService;
}
