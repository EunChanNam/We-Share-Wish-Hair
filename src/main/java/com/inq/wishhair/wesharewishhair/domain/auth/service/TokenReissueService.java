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

        if (!tokenManager.existByUserIdAndRefreshToken(userId, refreshToken)) {
            throw new WishHairException(ErrorCode.AUTH_INVALID_TOKEN);
        }

        if (!provider.isValidToken(refreshToken)) {
            throw new WishHairException(ErrorCode.AUTH_EXPIRED_TOKEN);
        }

        //토큰으로 부터 추출한 claims 가 유효하고 refreshToken 이 유효하다, access 토큰만 재발금
        String newAccessToken = provider.createAccessToken(userId);

        return TokenResponse.of(newAccessToken, refreshToken);
    }
}
