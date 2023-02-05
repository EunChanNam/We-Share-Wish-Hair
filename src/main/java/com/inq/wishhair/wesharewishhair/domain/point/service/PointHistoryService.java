package com.inq.wishhair.wesharewishhair.domain.point.service;

import com.inq.wishhair.wesharewishhair.domain.point.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;
}
