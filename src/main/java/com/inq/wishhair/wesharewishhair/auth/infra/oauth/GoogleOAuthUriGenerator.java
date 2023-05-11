package com.inq.wishhair.wesharewishhair.auth.infra.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleOAuthUriGenerator implements OAuthUriGenerator {

    private final OAuthProperties properties;

    @Override
    public String generate() {
        return properties.getAuthUrl() + "?"
                + "response_type=code&"
                + "client_id=" + properties.getClientId() + "&"
                + "scope=" + String.join(" ", properties.getScope()) + "&"
                + "redirect_uri=" + properties.getRedirectUrl();
    }
}
