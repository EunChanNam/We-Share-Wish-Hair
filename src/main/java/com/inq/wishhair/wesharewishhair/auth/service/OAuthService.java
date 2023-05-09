package com.inq.wishhair.wesharewishhair.auth.service;

import com.inq.wishhair.wesharewishhair.auth.exception.WishHairOAuthException;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.OAuthConnector;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.OAuthTokenResponse;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.OAuthUserResponse;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.LoginResponse;
import com.inq.wishhair.wesharewishhair.auth.utils.JwtTokenProvider;
import com.inq.wishhair.wesharewishhair.user.domain.Email;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OAuthService {

    private final OAuthConnector oAuthConnector;
    private final TokenManager tokenManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider provider;

    @Transactional
    public LoginResponse login(String code) {
        OAuthUserResponse userInfo = findUserInfoByOAuth(code);

        User user = findUserOrExceptionToSignUp(userInfo);

        String refreshToken = provider.createRefreshToken(user.getId());
        String accessToken = provider.createAccessToken(user.getId());

        tokenManager.synchronizeRefreshToken(user, refreshToken);

        return new LoginResponse(user, accessToken, refreshToken);
    }

    private OAuthUserResponse findUserInfoByOAuth(String code) {
        OAuthTokenResponse oauthToken = oAuthConnector.requestToken(code);
        return oAuthConnector.requestUserInfo(oauthToken.getAccessToken());
    }

    private User findUserOrExceptionToSignUp(OAuthUserResponse userInfo) {
        return userRepository.findByEmail(new Email(userInfo.getEmail()))
                .orElseThrow(() -> new WishHairOAuthException(userInfo));
    }
}
