package com.inq.wishhair.wesharewishhair.auth.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractToken;
import com.inq.wishhair.wesharewishhair.auth.service.TokenReissueService;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TokenReissueController {

    private final TokenReissueService tokenReissueService;

    @PostMapping("/token/reissue")
    public ResponseEntity<TokenResponse> reissueToken(@ExtractToken String refreshToken,
                                                      @ExtractPayload Long userId) {

        TokenResponse response = tokenReissueService.reissueToken(userId, refreshToken);
        return ResponseEntity.ok(response);
    }
}
