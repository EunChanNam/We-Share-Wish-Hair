package com.inq.wishhair.wesharewishhair.user.controller;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.user.controller.dto.response.PagedPointResponse;
import com.inq.wishhair.wesharewishhair.user.service.PointFindService;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
public class PointFindController {

    private final PointFindService pointFindService;

    @GetMapping
    public ResponseEntity<PagedPointResponse> findPointHistories(
            @ExtractPayload Long userId,
            @PageableDefault Pageable pageable) {

        Slice<PointResponse> result = pointFindService.findPointHistories(userId, pageable);

        return ResponseEntity.ok(new PagedPointResponse(result));
    }
}
