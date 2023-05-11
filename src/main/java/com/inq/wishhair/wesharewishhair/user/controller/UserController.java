package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.Success;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.*;
import com.inq.wishhair.wesharewishhair.user.service.UserService;
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
    public ResponseEntity<Success> signUp(@RequestBody SignUpRequest createRequest) {
        Long userId = userService.createUser(createRequest);

        return ResponseEntity
                .created(URI.create("/api/user/" + userId))
                .body(new Success());
    }

    @DeleteMapping
    public ResponseEntity<Success> deleteUser(@ExtractPayload Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.ok(new Success());
    }

    @PatchMapping("/refresh/password")
    public ResponseEntity<Success> refreshPassword(@RequestBody PasswordRefreshRequest request,
                                                   @ExtractPayload Long userId) {

        userService.refreshPassword(request, userId);

        return ResponseEntity.ok(new Success());
    }

    @PatchMapping
    public ResponseEntity<Success> updateUser(@RequestBody UserUpdateRequest request,
                                              @ExtractPayload Long userId) {

        userService.updateUser(userId, request);

        return ResponseEntity.ok(new Success());
    }

    @PatchMapping("/password")
    public ResponseEntity<Success> updatePassword(@RequestBody PasswordUpdateRequest request,
                                                  @ExtractPayload Long userId) {

        userService.updatePassword(userId, request);

        return ResponseEntity.ok(new Success());
    }

    @PatchMapping("/face_shape")
    public ResponseEntity<Success> updateFaceShape(@RequestBody FaceShapeUpdateRequest request,
                                                   @ExtractPayload Long userId) {

        userService.updateFaceShape(userId, request.getFaceShapeTag());

        return ResponseEntity.ok(new Success());
    }
}
