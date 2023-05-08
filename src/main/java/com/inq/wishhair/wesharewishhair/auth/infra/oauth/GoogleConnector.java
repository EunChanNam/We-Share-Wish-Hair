package com.inq.wishhair.wesharewishhair.auth.infra.oauth;

import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.GoogleTokenResponse;
import com.inq.wishhair.wesharewishhair.auth.infra.oauth.dto.response.GoogleUserResponse;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class GoogleConnector implements OAuthConnector{

    private static final String BEARER = "Bearer";

    private final RestTemplate restTemplate = new RestTemplate();
    private final OAuthProperties properties;

    @Override
    public GoogleTokenResponse requestToken(String code) {
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(generateTokenRequestBody(code), generateTokenRequestHeaders());

        return fetchGoogleToken(request);
    }

    private GoogleTokenResponse fetchGoogleToken(HttpEntity<MultiValueMap<String, String>> request) {
        ResponseEntity<GoogleTokenResponse> response;
        try {
            response = restTemplate.postForEntity(properties.getTokenUrl(), request, GoogleTokenResponse.class);
        } catch (RestClientException e) {
            throw new WishHairException(ErrorCode.GOOGLE_OAUTH_EXCEPTION);
        }

        validateResponseStatusIsOk(response.getStatusCode());
        return response.getBody();
    }

    private HttpHeaders generateTokenRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private MultiValueMap<String, String> generateTokenRequestBody(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("code", code);
        body.add("client_id", properties.getClientId());
        body.add("redirect_uri", properties.getRedirectUrl());
        body.add("grant_type", properties.getGrantType());
        return body;
    }

    @Override
    public GoogleUserResponse requestUserInfo(String accessToken) {
        HttpHeaders headers = generateUserInfoRequestHeader(accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        return fetchGoogleUserResponse(request);
    }

    private GoogleUserResponse fetchGoogleUserResponse(HttpEntity<Void> request) {
        ResponseEntity<GoogleUserResponse> response;
        try {
            response = restTemplate.postForEntity(properties.getUserInfoUrl(), request, GoogleUserResponse.class);
        } catch (RestClientException e) {
            throw new WishHairException(ErrorCode.GOOGLE_OAUTH_EXCEPTION);
        }

        validateResponseStatusIsOk(response.getStatusCode());
        return response.getBody();
    }

    private HttpHeaders generateUserInfoRequestHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, String.join(" ", BEARER, accessToken));
        return headers;
    }

    private void validateResponseStatusIsOk(HttpStatus status) {
        if (!status.is2xxSuccessful()) {
            throw new WishHairException(ErrorCode.GOOGLE_OAUTH_EXCEPTION);
        }
    }
}
