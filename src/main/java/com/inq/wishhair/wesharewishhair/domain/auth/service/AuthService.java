package com.inq.wishhair.wesharewishhair.domain.auth.service;

import com.inq.wishhair.wesharewishhair.domain.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.domain.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import com.inq.wishhair.wesharewishhair.domain.user.repository.UserRepository;
import com.inq.wishhair.wesharewishhair.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.exception.WishHairException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final TokenManager tokenManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider provider;

    public TokenResponse login(String loginId, String pw) {
        User user = userRepository.findByLoginId(loginId)
                .filter(findUser -> findUser.getPw().equals(pw))
                .orElseThrow(() -> new WishHairException(ErrorCode.LOGIN_FAIL));

        String accessToken = provider.createAccessToken(user.getId());
        String refreshToken = provider.createRefreshToken(user.getId());

        tokenManager.synchronizeRefreshToken(user, refreshToken);

        return TokenResponse.of(accessToken, refreshToken);
    }
}
