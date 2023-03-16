package com.inq.wishhair.wesharewishhair.web.auth;

import com.inq.wishhair.wesharewishhair.domain.auth.service.TokenReissueService;
import com.inq.wishhair.wesharewishhair.domain.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.domain.auth.utils.AuthorizationExtractor;
import com.inq.wishhair.wesharewishhair.domain.auth.utils.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
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
    private final JwtTokenProvider provider;

    @PostMapping("/token/reissue")
    public ResponseEntity<TokenResponse> reissueToken(HttpServletRequest request) {

        //todo ArgumentResolver 로 추상화 하기
        String refreshToken = AuthorizationExtractor.extract(request);
        Long userId = provider.getId(refreshToken);

        TokenResponse response = tokenReissueService.reissueToken(userId, refreshToken);
        return ResponseEntity.ok(response);
    }
}
