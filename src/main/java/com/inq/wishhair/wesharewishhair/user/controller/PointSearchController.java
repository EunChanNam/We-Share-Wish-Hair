package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.user.service.PointSearchService;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
public class PointSearchController {

    private final PointSearchService pointSearchService;

    @GetMapping
    public ResponseEntity<PagedResponse<PointResponse>> findPointHistories(
            @ExtractPayload Long userId,
            @PageableDefault Pageable pageable) {

        return ResponseEntity.ok(pointSearchService.findPointHistories(userId, pageable));
    }
}
