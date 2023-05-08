package com.inq.wishhair.wesharewishhair.auth.exception;

import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.OAuthUserResponse;
import lombok.Getter;

@Getter
public class WishHairOAuthException extends RuntimeException {
    private final OAuthUserResponse userInfo;

    public WishHairOAuthException(OAuthUserResponse userInfo) {
        super();
        this.userInfo = userInfo;
    }
}
