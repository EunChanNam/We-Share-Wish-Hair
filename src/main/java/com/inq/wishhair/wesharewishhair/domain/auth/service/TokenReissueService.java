package com.inq.wishhair.wesharewishhair.domain.auth.service;

import com.inq.wishhair.wesharewishhair.domain.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.domain.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.exception.WishHairException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenReissueService {

    private final TokenManager tokenManager;
    private final JwtTokenProvider provider;

    public TokenResponse reissueToken(Long userId, String refreshToken) {

        //사용하지 않은 RTR 토큰인지, 존재하는지 확인
        if (!tokenManager.existByUserIdAndRefreshToken(userId, refreshToken)) {
            throw new WishHairException(ErrorCode.AUTH_INVALID_TOKEN);
        }

        //access 토큰만 재발금
        String newAccessToken = provider.createAccessToken(userId);

        //refresh 토큰이 만료됐으면 새로운 Refresh 토큰 발급
        if (!provider.isValidToken(refreshToken)) {
            refreshToken = provider.createRefreshToken(userId);
        }

        return TokenResponse.of(newAccessToken, refreshToken);
    }
}
