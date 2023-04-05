package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.user.domain.point.PointSearchRepository;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointFindService {

    private final PointSearchRepository pointSearchRepository;

    public Slice<PointResponse> findPointHistories(Long userId, Pageable pageable) {

        return transferContentToResponse(pointSearchRepository.findByUserIdOrderByNew(userId, pageable));
    }

    private Slice<PointResponse> transferContentToResponse(Slice<PointHistory> slice) {
        return slice.map(PointResponse::new);
    }
}
