package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.user.service.UserService;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Void> createUser(@RequestBody UserCreateRequest createRequest) {
        Long userId = userService.createUser(createRequest.toEntity());

        return ResponseEntity
                .created(URI.create("/api/user/" + userId))
                .build();
    }
}
