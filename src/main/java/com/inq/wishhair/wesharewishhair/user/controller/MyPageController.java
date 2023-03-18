package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.user.controller.dto.response.MyPageResponse;
import com.inq.wishhair.wesharewishhair.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/my_page")
    public ResponseEntity<MyPageResponse> getMyPageInfo(
            @ExtractPayload Long userId) {

        MyPageResponse response = myPageService.getMyPageInfo(userId);

        return ResponseEntity.ok(response);
    }
}
