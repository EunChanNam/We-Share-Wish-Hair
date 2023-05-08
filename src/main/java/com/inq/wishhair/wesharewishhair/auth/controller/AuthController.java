package com.inq.wishhair.wesharewishhair.auth.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.auth.service.AuthService;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.auth.controller.dto.request.LoginRequest;
import com.inq.wishhair.wesharewishhair.global.dto.response.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse response = authService.login(loginRequest.getEmail(), loginRequest.getPw());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Success> logout(@ExtractPayload Long userId) {
        authService.logout(userId);
        return ResponseEntity.ok(new Success());
    }
}
