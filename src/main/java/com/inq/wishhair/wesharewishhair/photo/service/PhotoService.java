package com.inq.wishhair.wesharewishhair.photo.service;

import com.inq.wishhair.wesharewishhair.photo.domain.Photo;
import com.inq.wishhair.wesharewishhair.photo.domain.PhotoRepository;
import com.inq.wishhair.wesharewishhair.photo.utils.PhotoStore;
import com.inq.wishhair.wesharewishhair.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoStore photoStore;
    private final PhotoRepository photoRepository;

    public List<String> uploadPhotos(List<MultipartFile> files) {
        return photoStore.uploadFiles(files);
    }

    @Transactional
    public void deletePhotosByReviewId(Review review) {
        deletePhotosInCloud(review);
        photoRepository.deleteAllByReview(review.getId());
    }

    @Transactional
    public void deletePhotosByWriter(List<Review> reviews) {
        reviews.forEach(this::deletePhotosInCloud);
        photoRepository.deleteAllByReviews(reviews.stream().map(Review::getId).toList());
    }

    private void deletePhotosInCloud(Review review) {
        List<String> storeUrls = review.getPhotos().stream().map(Photo::getStoreUrl).toList();
        photoStore.deleteFiles(storeUrls);
    }
}
