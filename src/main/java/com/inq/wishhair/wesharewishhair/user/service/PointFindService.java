package com.inq.wishhair.wesharewishhair.user.service;

import com.inq.wishhair.wesharewishhair.user.domain.point.PointFindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointFindService {

    private final PointFindRepository pointFindRepository;
}
