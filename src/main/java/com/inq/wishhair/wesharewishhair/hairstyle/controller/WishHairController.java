package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.Success;
import com.inq.wishhair.wesharewishhair.hairstyle.service.WishHairService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hair_style/wish/")
public class WishHairController {

    private final WishHairService wishHairService;

    @PostMapping("/{hairStyleId}")
    public ResponseEntity<Success> executeWish(
            @PathVariable Long hairStyleId,
            @ExtractPayload Long userId) {

        wishHairService.executeWish(hairStyleId, userId);

        return ResponseEntity.ok(new Success());
    }

    @DeleteMapping("/{hairStyleId}")
    public ResponseEntity<Success> cancelWish(@PathVariable Long hairStyleId,
                                              @ExtractPayload Long userId) {

        wishHairService.cancelWish(hairStyleId, userId);

        return ResponseEntity.ok(new Success());
    }
}
