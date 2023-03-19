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

    @Transactional
    public TokenResponse reissueToken(Long userId, String refreshToken) {

        //사용하지 않은 RTR 토큰인지, 존재하는지 확인
        if (!tokenManager.existByUserIdAndRefreshToken(userId, refreshToken)) {
            throw new WishHairException(ErrorCode.AUTH_INVALID_TOKEN);
        }

        String newAccessToken = provider.createAccessToken(userId);
        String newRefreshToken = provider.createRefreshToken(userId);

        tokenManager.updateRefreshTokenByRTR(userId, newRefreshToken);

        return TokenResponse.of(newAccessToken, newRefreshToken);
    }
}
