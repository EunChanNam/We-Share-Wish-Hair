package com.inq.wishhair.wesharewishhair.fixture;

import com.inq.wishhair.wesharewishhair.auth.service.dto.response.TokenResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenFixture {

    A("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiaWF0IjoxNjc3OTM3MjI0LCJleHAiOjE2Nzc5NDQ0MjR9.t61tw4gDEBuXBn_DnCwiPIDaI-KcN9Zkn3QJSEK7fag",
            "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiaWF0IjoxNjc3OTM3MjI0LCJleHAiOjE2Nzg1NDIwMjR9.doqGa5Hcq6chjER1y5brJEv81z0njcJqeYxJb159ZX4");

    private final String accessToken;
    private final String refreshToken;

    public TokenResponse toTokenResponse() {
        return TokenResponse.of(accessToken, refreshToken);
    }
}
