package com.inq.wishhair.wesharewishhair.auth.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.auth.service.AuthService;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.auth.controller.dto.request.LoginRequest;
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

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@ModelAttribute LoginRequest loginRequest) {

        TokenResponse response = authService.login(loginRequest.getLoginId(), loginRequest.getPw());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> logout(@ExtractPayload Long userId) {

        authService.logout(userId);

        return ResponseEntity.noContent().build();
    }
}
