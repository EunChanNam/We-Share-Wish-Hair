package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.MyPageResponse;
import com.inq.wishhair.wesharewishhair.user.service.UserInfoService;
import com.inq.wishhair.wesharewishhair.auth.service.dto.response.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping("/my_page")
    public ResponseEntity<MyPageResponse> getMyPageInfo(@ExtractPayload Long userId) {

        MyPageResponse result = userInfoService.getMyPageInfo(userId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/info")
    public ResponseEntity<UserInfo> getUserInformation(@ExtractPayload Long userId) {

        UserInfo result = userInfoService.getUserInformation(userId);

        return ResponseEntity.ok(result);
    }
}
