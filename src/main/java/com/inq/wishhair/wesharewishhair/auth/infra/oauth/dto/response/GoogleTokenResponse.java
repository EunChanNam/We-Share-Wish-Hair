package com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleTokenResponse implements OAuthTokenResponse{

    @JsonProperty("access_token")
    private String accessToken;
}
