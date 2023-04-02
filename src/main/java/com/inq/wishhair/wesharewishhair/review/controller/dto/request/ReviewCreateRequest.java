package com.inq.wishhair.wesharewishhair.review.controller.dto.request;

import com.inq.wishhair.wesharewishhair.review.enums.Score;
import com.inq.wishhair.wesharewishhair.review.service.dto.ReviewCreateDto;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewCreateRequest {

    @NotNull
    private String contents;

    @NotNull
    private Score score;

    private List<MultipartFile> files = new ArrayList<>();

    @NotNull
    private Long hairStyleId;
}
