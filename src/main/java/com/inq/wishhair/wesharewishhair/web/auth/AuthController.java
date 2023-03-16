package com.inq.wishhair.wesharewishhair.web.auth;

import com.inq.wishhair.wesharewishhair.domain.auth.service.AuthService;
import com.inq.wishhair.wesharewishhair.domain.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.domain.auth.utils.AuthorizationExtractor;
import com.inq.wishhair.wesharewishhair.domain.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.web.login.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider provider;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@ModelAttribute LoginRequest loginRequest) {

        TokenResponse response = authService.login(loginRequest.getLoginId(), loginRequest.getPw());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> logout(HttpServletRequest request) {

        //todo ArgumentResolver 로 추상화 하기
        String token = AuthorizationExtractor.extract(request);
        authService.logout(provider.getId(token));

        return ResponseEntity.noContent().build();
    }
}
