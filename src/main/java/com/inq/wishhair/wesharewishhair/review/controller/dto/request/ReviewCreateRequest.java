package com.inq.wishhair.wesharewishhair.review.controller.dto.request;

import com.inq.wishhair.wesharewishhair.review.domain.enums.Score;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewCreateRequest {

    @NotNull
    private String contents;

    @NotNull
    private Score score;

    private List<MultipartFile> files;

    @NotNull
    private Long hairStyleId;
}
