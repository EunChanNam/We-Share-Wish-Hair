package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.Success;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.PasswordUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.service.UserService;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Success> createUser(@RequestBody UserCreateRequest createRequest) {
        Long userId = userService.createUser(createRequest.toEntity());

        return ResponseEntity
                .created(URI.create("/api/user/" + userId))
                .body(new Success());
    }

    @DeleteMapping
    public ResponseEntity<Success> deleteUser(@ExtractPayload Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.ok(new Success());
    }

    @PatchMapping
    public ResponseEntity<Success> updateUser(@RequestBody UserUpdateRequest request,
                                              @ExtractPayload Long userId) {

        userService.updateUser(userId, request.updateDto());

        return ResponseEntity.ok(new Success());
    }

    @PatchMapping
    public ResponseEntity<Success> updatePassword(@RequestBody PasswordUpdateRequest request,
                                                  @ExtractPayload Long userId) {

        userService.updatePassword(userId, request.toPasswordUpdateRequest());

        return ResponseEntity.ok(new Success());
    }
}
