package com.inq.wishhair.wesharewishhair.user.utils;

import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

public class FlaskConnector implements AiConnector{

    private static final String URL = "/fileupload";
    private static final String FILES = "files";

    private final String requestUri;
    private final RestTemplate restTemplate;

    public FlaskConnector(@Value("${flask.domain}") String domain) {
        this.requestUri = domain + URL;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Tag detectFaceShape(MultipartFile file) {
        validateFileExist(file);

        HttpHeaders headers = generateHeaders();
        MultiValueMap<String, Object> body = generateBody(file);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

        String response = fetchFlackResponse(request);
        return toTag(response);
    }

    private String fetchFlackResponse(HttpEntity<MultiValueMap<String, Object>> request) {
        ResponseEntity<String> response;
        try {
            response = restTemplate.postForEntity(requestUri, request, String.class);
        } catch (RestClientException e) {
            throw new WishHairException(ErrorCode.FLASK_SERVER_EXCEPTION);
        }

        validateResponseStatusIsOk(response.getStatusCode());
        return response.getBody();
    }

    private HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

    private MultiValueMap<String, Object> generateBody(MultipartFile file) {
        LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(FILES, file.getResource());
        return body;
    }

    private void validateResponseStatusIsOk(HttpStatus status) {
        if (!status.is2xxSuccessful()) {
            throw new IllegalStateException("상태코드 예외 발생");
        }
    }

    private void validateFileExist(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new WishHairException(ErrorCode.EMPTY_FILE_EX);
        }
    }

    private Tag toTag(String response) {
        try {
            return Tag.valueOf(response);
        } catch (IllegalArgumentException e) {
            throw new WishHairException(ErrorCode.FLASK_RESPONSE_ERROR);
        }
    }
}
