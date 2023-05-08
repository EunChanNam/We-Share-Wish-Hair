package com.inq.wishhair.wesharewishhair.auth.controller;

import com.inq.wishhair.wesharewishhair.auth.infra.oauth.OAuthUriGenerator;
import com.inq.wishhair.wesharewishhair.auth.service.OAuthService;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.LoginResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.SimpleResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthUriGenerator uriGenerator;
    private final OAuthService oAuthService;

    @GetMapping("/access")
    public ResponseEntity<SimpleResponseWrapper<String>> access() {
        String link = uriGenerator.generate();
        return ResponseEntity.ok(new SimpleResponseWrapper<>(link));
    }

    @GetMapping(value = "/login", params = "authorizationCode")
    public ResponseEntity<LoginResponse> login(@RequestParam String authorizationCode) {
        LoginResponse response = oAuthService.login(authorizationCode);

        return ResponseEntity.ok(response);
    }
}
