package com.inq.wishhair.wesharewishhair.review.controller.dto.request;

import com.inq.wishhair.wesharewishhair.review.domain.Score;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewUpdateRequest {

    @NotNull
    private Long reviewId;

    @NotNull
    private String contents;

    @NotNull
    private Score score;

    private List<MultipartFile> files;
}
