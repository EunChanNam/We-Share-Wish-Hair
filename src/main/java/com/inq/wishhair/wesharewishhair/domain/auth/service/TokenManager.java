package com.inq.wishhair.wesharewishhair.domain.auth.service;

import com.inq.wishhair.wesharewishhair.domain.auth.Token;
import com.inq.wishhair.wesharewishhair.domain.auth.repository.TokenRepository;
import com.inq.wishhair.wesharewishhair.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenManager {

    private final TokenRepository tokenRepository;

    @Transactional
    public void synchronizeRefreshToken(User user, String refreshToken) {
        tokenRepository.findByUserAndRefreshToken(user, refreshToken)
                .ifPresentOrElse(
                        token -> token.updateRefreshToken(refreshToken),
                        () -> tokenRepository.save(Token.issue(user, refreshToken))
                );
    }
}
