package com.inq.wishhair.wesharewishhair.web.login;

import com.inq.wishhair.wesharewishhair.domain.login.LoginService;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.web.common.consts.SessionConst;
import com.inq.wishhair.wesharewishhair.web.login.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(LoginRequest loginRequest, HttpServletRequest request) {

        UserSessionDto sessionDto = loginService.login(loginRequest.getLoginId(), loginRequest.getPw());
        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LONGIN_MEMBER, sessionDto);

        return ResponseEntity.noContent().build();
    }
}
