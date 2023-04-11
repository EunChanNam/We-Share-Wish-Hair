package com.inq.wishhair.wesharewishhair.review.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public record PointChargeEvent(int dealAmount, Long userId) {

}
