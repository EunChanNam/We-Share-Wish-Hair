package com.inq.wishhair.wesharewishhair.common.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inq.wishhair.wesharewishhair.domain.hairstyle.service.HairStyleService;
import com.inq.wishhair.wesharewishhair.domain.login.LoginService;
import com.inq.wishhair.wesharewishhair.domain.point.service.PointHistoryService;
import com.inq.wishhair.wesharewishhair.domain.user.service.UserService;
import com.inq.wishhair.wesharewishhair.web.hairstyle.HairStyleController;
import com.inq.wishhair.wesharewishhair.web.login.LoginController;
import com.inq.wishhair.wesharewishhair.web.user.UserController;
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
    protected PointHistoryService pointService;

    @MockBean
    protected LoginService loginService;

    protected String getJsonAsString(Object target) throws JsonProcessingException {
        return objectMapper.writeValueAsString(target);
    }
}
