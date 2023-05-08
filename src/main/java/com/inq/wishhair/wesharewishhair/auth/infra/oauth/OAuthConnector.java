package com.inq.wishhair.wesharewishhair.auth.infra.oauth;

import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.GoogleTokenResponse;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.GoogleUserResponse;

public interface OAuthConnector {

    GoogleTokenResponse requestToken(String code);

    GoogleUserResponse requestUserInfo(String accessToken);
}
