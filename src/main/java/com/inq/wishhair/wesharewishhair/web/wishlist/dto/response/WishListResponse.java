package com.inq.wishhair.wesharewishhair.web.wishlist.dto.response;

import com.inq.wishhair.wesharewishhair.hairstyle.domain.HairStyle;
import com.inq.wishhair.wesharewishhair.web.photo.dto.response.PhotoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WishListResponse {

    private Long hairStyleId;

    private String hairStyleName;

    private PhotoResponse photoResponse;

    public WishListResponse(HairStyle hairStyle) {
        this.hairStyleId = hairStyle.getId();
        this.hairStyleName = hairStyle.getName();
        if (!hairStyle.getPhotos().isEmpty()) {
            this.photoResponse = new PhotoResponse(hairStyle.getPhotos().get(0));
        }
    }
}
