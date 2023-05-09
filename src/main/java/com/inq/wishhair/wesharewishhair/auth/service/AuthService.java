package com.inq.wishhair.wesharewishhair.auth.service;

import com.inq.wishhair.wesharewishhair.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.user.domain.Email;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final TokenManager tokenManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider provider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponse login(String email, String pw) {
        User user = userRepository.findByEmail(new Email(email))
                .filter(findUser -> passwordEncoder.matches(pw, findUser.getPasswordValue()))
                .orElseThrow(() -> new WishHairException(ErrorCode.LOGIN_FAIL));

        String refreshToken = provider.createRefreshToken(user.getId());
        String accessToken = provider.createAccessToken(user.getId());

        tokenManager.synchronizeRefreshToken(user, refreshToken);

        return TokenResponse.of(accessToken, refreshToken);
    }

    @Transactional
    public void logout(Long userId) {
        tokenManager.deleteToken(userId);
    }
}
