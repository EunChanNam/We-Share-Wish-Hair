package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.MyPageResponse;
import com.inq.wishhair.wesharewishhair.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyPageController {

    private final UserInfoService userInfoService;

    @GetMapping("/my_page")
    public ResponseEntity<MyPageResponse> getMyPageInfo(
            @ExtractPayload Long userId) {

        MyPageResponse response = userInfoService.getMyPageInfo(userId);

        return ResponseEntity.ok(response);
    }
}
