package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.MyPageResponse;
import com.inq.wishhair.wesharewishhair.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.inq.wishhair.wesharewishhair.review.common.ReviewSortCondition.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/my_page")
    public ResponseEntity<MyPageResponse> getMyPageInfo(
            @ExtractPayload Long userId,
            @PageableDefault(size = 3, sort = DATE, direction = Sort.Direction.ASC) Pageable pageable) {

        MyPageResponse response = myPageService.getMyPageInfo(userId, pageable);

        return ResponseEntity.ok(response);
    }
}
