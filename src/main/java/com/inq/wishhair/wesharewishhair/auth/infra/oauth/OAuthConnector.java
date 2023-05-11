package com.inq.wishhair.wesharewishhair.auth.infra.oauth;

import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.OAuthTokenResponse;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.OAuthUserResponse;

public interface OAuthConnector {

    OAuthTokenResponse requestToken(String code);

    OAuthUserResponse requestUserInfo(String accessToken);
}
