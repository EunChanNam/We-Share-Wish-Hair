package com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.enums.TagType., NORMAL;

@Getter
@AllArgsConstructor
public enum Tag {
    LONG("긴 머리", NORMAL), MEDIUM("중간 머리", NORMAL), SHORT("짧은 머리", NORMAL), PERM("펌 O", NORMAL),
    NO_PERM("펌 X", NORMAL), BANGS("앞머리 O", NORMAL), NO_BANGS("앞머리 X", NORMAL), STRAIGHT("직모", NORMAL),
    CURLY("곱슬", NORMAL), SIMPLE("손질 간단", NORMAL), HARD("손질 필요", NORMAL), LOVELY("사랑스러운", NORMAL),
    LIGHT("가벼운", NORMAL), HEAVY("무거운", NORMAL), NEAT("깔끔한", NORMAL), FORMAL("포멀한", NORMAL),
    COMMON("무난한", NORMAL), NATURAL("자연스러운", NORMAL), COOL("시원한", NORMAL), MANLY("남성적인", NORMAL),
    CUTE("귀여운", NORMAL), UPSTAGE("도도한", NORMAL), VOLUMINOUS("볼륨있는", NORMAL),LONG_LASTING("오래가는", NORMAL),
    SOFT("부드러운", NORMAL), TRENDY("트렌디한", NORMAL), ROUND("둥근형", FACE_SHAPE), OVAL("타원형", FACE_SHAPE),
    HEART("하트형", FACE_SHAPE), OBLONG("직사각형", FACE_SHAPE), SQUARE("정사각형", FACE_SHAPE), ALL("전체", NORMAL),
    Error("No-Tag", NORMAL);

    private final String description;
    private final TagType tagType;
}
