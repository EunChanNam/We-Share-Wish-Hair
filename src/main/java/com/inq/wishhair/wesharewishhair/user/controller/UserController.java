package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.user.domain.point.domain.PointHistory;
import com.inq.wishhair.wesharewishhair.user.service.UserPointService;
import com.inq.wishhair.wesharewishhair.user.domain.User;
import com.inq.wishhair.wesharewishhair.user.service.UserService;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserCreateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.response.MyPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserPointService pointHistoryService;

    @PostMapping("/user")
    public ResponseEntity<Void> createUser(@ModelAttribute UserCreateRequest createRequest) {
        Long userId = userService.createUser(createRequest.toEntity());

        return ResponseEntity
                .created(URI.create("/api/user/" + userId))
                .build();
    }

    @GetMapping("/my_page")
    public ResponseEntity<MyPageResponse> getMyPageInfo(
            @ExtractPayload Long userId) {

        //todo myPageService 따로 만들어서 트랜잭션 통일 -> user-service 구조에
        PointHistory recentPointHistory = pointHistoryService.getRecentPointHistory(userId);
        User user = userService.findByUserId(userId);

        MyPageResponse myPageResponse = new MyPageResponse(user, recentPointHistory);

        return ResponseEntity.ok(myPageResponse);
    }
}
