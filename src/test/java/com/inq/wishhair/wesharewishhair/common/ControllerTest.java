package com.inq.wishhair.wesharewishhair.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inq.wishhair.wesharewishhair.domain.user.service.UserService;
import com.inq.wishhair.wesharewishhair.web.user.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({UserController.class})
public abstract class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;
}
