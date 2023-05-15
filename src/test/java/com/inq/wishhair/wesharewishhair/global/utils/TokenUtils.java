package com.inq.wishhair.wesharewishhair.global.utils;


import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.GoogleTokenResponse;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.LoginResponse;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.TokenResponse;
import com.inq.wishhair.wesharewishhair.global.fixture.UserFixture;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import com.inq.wishhair.wesharewishhair.user.domain.FaceShape;
import com.inq.wishhair.wesharewishhair.user.domain.User;

public abstract class TokenUtils {

    //1L 아이디를 통해 만든 토큰이다
    public static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImlhdCI6MTY3OTI4NjMyOCwiZXhwIjoxNjc5MjkzNTI4fQ.WW-C8q2EWMlQsZ_ckFZSvTXWONbvUn1uW-PAICzv578";
    public static final String REFRESH_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImlhdCI6MTY3OTI4NjM4MSwiZXhwIjoxNjgwNDk1OTgxfQ.sVoSA4TtC-DwivOn66ax7o8iFi83uluPy2TB-f3xAgc";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";

    public static LoginResponse toLoginResponse(UserFixture fixture) {
        User user = fixture.toEntity();
        user.updateFaceShape(new FaceShape(Tag.ROUND));
        return new LoginResponse(user, ACCESS_TOKEN, REFRESH_TOKEN);
    }

    public static TokenResponse toTokenResponse() {
        return TokenResponse.of(ACCESS_TOKEN, REFRESH_TOKEN);
    }

    public static GoogleTokenResponse toGoogleTokenResponse() {
        return new GoogleTokenResponse(ACCESS_TOKEN);
    }
}
