package com.project.reviewms.review.impl;

import com.project.reviewms.review.Review;
import com.project.reviewms.review.ReviewRepository;
import com.project.reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        if(companyId == null || review == null) return false;

        review.setCompanyId(companyId);
        reviewRepository.save(review);
        return true;
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return true;
    }

    @Override
    public boolean updateReview(Long reviewId, Review updatedReview) {
        Review reviewToUpdate = reviewRepository.findById(reviewId).orElse(null);

        if(reviewToUpdate == null) return false;

        reviewToUpdate.setTitle(updatedReview.getTitle());
        reviewToUpdate.setDescription(updatedReview.getDescription());
        reviewToUpdate.setRating(updatedReview.getRating());

        reviewRepository.save(reviewToUpdate);
        return true;
    }


}
