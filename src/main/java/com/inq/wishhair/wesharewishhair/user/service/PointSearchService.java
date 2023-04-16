package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.utils.PageableUtils;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointSearchRepository;
import com.inq.wishhair.wesharewishhair.user.domain.point.PointHistory;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.PointResponse;
import com.inq.wishhair.wesharewishhair.user.service.dto.response.UserResponseAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.inq.wishhair.wesharewishhair.global.utils.PageableUtils.getDefaultPageable;
import static com.inq.wishhair.wesharewishhair.user.service.dto.response.UserResponseAssembler.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointSearchService {

    private final PointSearchRepository pointSearchRepository;

    public PagedResponse<PointResponse> findPointHistories(Long userId) {

        Slice<PointHistory> result = pointSearchRepository.findByUserIdOrderByNew(userId, getDefaultPageable());
        return toPagedResponse(result);
    }
}
