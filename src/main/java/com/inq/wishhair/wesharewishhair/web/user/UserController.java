package com.inq.wishhair.wesharewishhair.web.user;

import com.inq.wishhair.wesharewishhair.common.consts.SessionConst;
import com.inq.wishhair.wesharewishhair.domain.login.dto.UserSessionDto;
import com.inq.wishhair.wesharewishhair.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.domain.point.service.PointHistoryService;
import com.inq.wishhair.wesharewishhair.domain.user.service.UserService;
import com.inq.wishhair.wesharewishhair.web.user.dto.request.UserCreateRequest;
import com.inq.wishhair.wesharewishhair.web.user.dto.response.MyPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final PointHistoryService pointHistoryService;

    @PostMapping("/user")
    public ResponseEntity<Void> createUser(@ModelAttribute UserCreateRequest createRequest) {
        Long userId = userService.createUser(createRequest.toEntity());

        return ResponseEntity
                .created(URI.create("/api/user/" + userId))
                .build();
    }

    @GetMapping("/my_page")
    public ResponseEntity<MyPageResponse> getMyPageInfo(
            @SessionAttribute(SessionConst.LONGIN_MEMBER) UserSessionDto sessionDto) {


        PointHistory recentPointHistory = pointHistoryService.getRecentPointHistory(sessionDto.getId());
        MyPageResponse myPageResponse = new MyPageResponse(sessionDto, recentPointHistory);

        return ResponseEntity.ok(myPageResponse);
    }
}
