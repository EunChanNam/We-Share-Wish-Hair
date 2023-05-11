package com.inq.wishhair.wesharewishhair.auth.infra.oauth;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Getter
@Component
public class OAuthProperties {
    private final String grantType;
    private final String clientId;
    private final String redirectUrl;
    private final Set<String> scope;
    private final String authUrl;
    private final String tokenUrl;
    private final String userInfoUrl;

    public OAuthProperties(
            @Value("${oauth2.google.grant-type}") String grantType,
            @Value("${oauth2.google.client-id}") String clientId,
            @Value("${oauth2.google.redirect-url}") String redirectUrl,
            @Value("${oauth2.google.scope}") Set<String> scope,
            @Value("${oauth2.google.auth-url}") String authUrl,
            @Value("${oauth2.google.token-url}") String tokenUrl,
            @Value("${oauth2.google.user-info-url}") String userInfoUrl
    ) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.redirectUrl = redirectUrl;
        this.scope = scope;
        this.authUrl = authUrl;
        this.tokenUrl = tokenUrl;
        this.userInfoUrl = userInfoUrl;
    }
}
