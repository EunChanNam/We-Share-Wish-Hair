package com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserResponse implements OAuthUserResponse{

    private String name;
    private String email;
}
