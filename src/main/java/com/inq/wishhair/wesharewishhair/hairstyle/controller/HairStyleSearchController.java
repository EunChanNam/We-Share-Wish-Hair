package com.inq.wishhair.wesharewishhair.hairstyle.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inq.wishhair.wesharewishhair.auth.config.resolver.ExtractPayload;
import com.inq.wishhair.wesharewishhair.global.dto.response.PagedResponse;
import com.inq.wishhair.wesharewishhair.global.dto.response.ResponseWrapper;
import com.inq.wishhair.wesharewishhair.global.exception.ErrorCode;
import com.inq.wishhair.wesharewishhair.global.exception.WishHairException;
import com.inq.wishhair.wesharewishhair.hairstyle.domain.hashtag.Tag;
import com.inq.wishhair.wesharewishhair.hairstyle.service.HairStyleSearchService;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.HairStyleSimpleResponse;
import com.inq.wishhair.wesharewishhair.hairstyle.service.dto.response.RecommendResponse;
import com.inq.wishhair.wesharewishhair.user.controller.dto.request.FaceShapeUpdateRequest;
import com.inq.wishhair.wesharewishhair.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hair_style")
public class HairStyleSearchController {

	private final HairStyleSearchService hairStyleSearchService;
	private final UserService userService;

	@PostMapping("/recommend")
	public ResponseEntity<RecommendResponse> respondRecommendedHairStyle(
		@ModelAttribute FaceShapeUpdateRequest request,
		@RequestParam(defaultValue = "ERROR") List<Tag> tags,
		@ExtractPayload Long userId
	) {
		validateHasTag(tags);

		RecommendResponse recommendResponse = userService.recommendHairStyle(
			userId,
			request.getFile(),
			tags
		);

		return ResponseEntity.ok(recommendResponse);
	}

	@GetMapping("/home")
	public ResponseWrapper<HairStyleResponse> findHairStyleByFaceShape(
		@ExtractPayload Long userId) {

		return hairStyleSearchService.recommendHairByFaceShape(userId);
	}

	@GetMapping("/wish")
	public PagedResponse<HairStyleResponse> findWishHairStyles(@ExtractPayload Long useId,
		@PageableDefault Pageable pageable) {

		return hairStyleSearchService.findWishHairStyles(useId, pageable);
	}

	@GetMapping
	public ResponseWrapper<HairStyleSimpleResponse> findAllHairStyles() {
		return hairStyleSearchService.findAllHairStyle();
	}

	private void validateHasTag(List<Tag> tags) {
		if (tags.get(0).equals(Tag.ERROR)) {
			throw new WishHairException(ErrorCode.RUN_NOT_ENOUGH_TAG);
		}
	}
}
