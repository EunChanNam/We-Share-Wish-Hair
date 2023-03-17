package com.inq.wishhair.wesharewishhair.auth.service;

import com.inq.wishhair.wesharewishhair.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
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

        //RefreshToken 이 만료됐으면 다시 로그인을 통해 인증하도록 에러던짐
        if (!provider.isValidToken(refreshToken)) {
            throw new WishHairException(ErrorCode.AUTH_EXPIRED_TOKEN);
        }

        String newAccessToken = provider.createAccessToken(userId);
        String newRefreshToken = provider.createRefreshToken(userId);

        tokenManager.updateRefreshTokenByRTR(userId, newRefreshToken);

        return TokenResponse.of(newAccessToken, newRefreshToken);
    }
}
