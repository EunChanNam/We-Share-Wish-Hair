package com.inq.wishhair.wesharewishhair.domain.hashtag.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Tag {
    LONG("긴 머리"), MEDIUM("중간 머리"), SHORT("짧은 머리"), PERM("펌 O"),
    NO_PERM("펌 X"), BANGS("앞머리 O"), NO_BANGS("앞머리 X"),
    STRAIGHT("직모"), CURLY("곱슬"), SIMPLE("손질 간단"), HARD("손질 필요"),
    LIGHT("가벼운"), HEAVY("무거운"), NEAT("깔끔한"), FORMAL("포멀한"),
    NATURAL("자연스러운"), COOL("시원한"), MANLY("남성적인"), CUTE("귀여운"),
    SOFT("부드러운"), TRENDY("트렌디한"), ROUND("둥근형"), OVAL("타원형"),
    HEART("하트형"), OBLONG("직사각형"), SQUARE("정사각형");

    private final String description;
}
